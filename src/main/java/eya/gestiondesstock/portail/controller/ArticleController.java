package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.*;
import eya.gestiondesstock.portail.entity.dto.AddArticleDTO;
import eya.gestiondesstock.portail.entity.dto.ArticleEmplacementDTO;
import eya.gestiondesstock.portail.entity.dto.DashArtMouvDTO;
import eya.gestiondesstock.portail.entity.dto.EmplacementDTO;
import eya.gestiondesstock.portail.filter.ImageUtility;
import eya.gestiondesstock.portail.repository.ArticleRepository;
import eya.gestiondesstock.portail.repository.CategoryRepository;
import eya.gestiondesstock.portail.repository.ImageRepository;
import eya.gestiondesstock.portail.services.QRCodeService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("dash/top-five")
    public List<Article> topFive() {
        return articleRepository.topFive();
    }


    @GetMapping("dash/findTop5ArticlesByEmplacementCount")
    public List<DashArtMouvDTO> findTop5ArticlesByEmplacementCount() {
       // return articleRepository.findTop5ArticlesByEmplacementCount();


        List<Object[]> results = articleRepository.findTop5ArticlesByEmplacementCount();

        return results.stream().map(row -> new DashArtMouvDTO(
                (String) row[0], // empEnum
                ((Number) row[1]).intValue()
        )).collect(Collectors.toList());
    }



    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static long getCurrentTimeMillis() {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = localDate.atTime(LocalTime.now());
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }


    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody AddArticleDTO addArticleDTO) {
        Category category = categoryRepository.findById(Long.valueOf(addArticleDTO.getCategoryId())).orElse(null);
        if(category == null){
          return ResponseEntity.badRequest().body("category not found");
        }
        Article article = new Article();
        article.setName(addArticleDTO.getName());
        article.setCodeBarre(String.valueOf(getCurrentTimeMillis()));
        article.setQuantityTotal(0);
        article.setCategory(category);
        return ResponseEntity.ok(articleRepository.save(article));
    }

    @Transactional
    @PostMapping("image/{id}")
    public ResponseEntity<?> affectImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable Long id
    ) throws IOException {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null){
            return ResponseEntity.badRequest().body("Article non trouvé");
        }
        Image image = new Image();
        image.setArticle(article);
        image.setType(file.getContentType());
        image.setName(file.getOriginalFilename());
        image.setImage(file.getBytes());
        Image imageSaved = imageRepository.save(image);

        article.addImageList(imageSaved);


        return ResponseEntity.ok(articleRepository.save(article)) ;
    }

    @PutMapping("/{id}/{idCategory}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id,@PathVariable Long idCategory, @RequestBody Article articleDetails) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        Category category = categoryRepository.findById(idCategory).orElse(null);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            article.setName(articleDetails.getName());
            article.setCategory(category);
            article.setQuantityTotal(articleDetails.getQuantityTotal());

            Article updatedArticle = articleRepository.save(article);
            return ResponseEntity.ok(updatedArticle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/byCategory/{categoryId}")
    public List<Article> getArticlesByCategoryId(@PathVariable Long categoryId) {
        return articleRepository.findByCategoryId(categoryId);
    }

    @GetMapping("/getArticle/byCodeBarre")
    public ResponseEntity<Article> getArticleByCodeBarre(@RequestParam String codeBarre) {
        Optional<Article> article = articleRepository.findByCodeBarre(codeBarre);
        return article.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/qrcode")
    public void generateQRCode(HttpServletResponse response,
                               @RequestParam String text,
                               @RequestParam(defaultValue = "350") int width,
                               @RequestParam(defaultValue = "350") int height) throws Exception {
        BufferedImage image = qrCodeService.generateQRCode(text, width, height);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
