package com.tandanji.taco_core_server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tandanji.taco_core_server.application.ProductsService;
import com.tandanji.taco_core_server.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest  // 실제 스프링 컨텍스트에서 테스트
@Transactional
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;  // 실제 컨트롤러를 주입

    @Autowired
    private ProductsService productsService;  // 실제 서비스 로직이 주입됩니다.

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        // given: 테스트용 Product 객체 생성
        Product product = new Product();
        product.setTitle("도라지");
        product.setDescription("맛있는 도라지");
        product.setCategory("약초");
        product.setLocation("서울");

        // Product 객체를 JSON 형식으로 변환
        String productJson = new ObjectMapper().writeValueAsString(product);

        // 파일 생성 (이미지)
        byte[] imageContent = "fake image content".getBytes();  // 더미 이미지 파일 내용
        MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", imageContent);

        // Product JSON을 멀티파트로 추가
        MockMultipartFile productPart = new MockMultipartFile("product", "product.json", "application/json", productJson.getBytes());

        // when: POST 요청을 보내는 테스트
        MvcResult result = mockMvc.perform(multipart("/product/create")
                        .file(imageFile)  // 이미지 파일 추가
                        .file(productPart)  // Product JSON을 파라미터로 전달
                        .contentType(MediaType.MULTIPART_FORM_DATA))  // 멀티파트 폼 데이터
                .andExpect(status().isCreated())  // 상태 코드 201 (Created) 검증
                .andReturn();
        }
}
