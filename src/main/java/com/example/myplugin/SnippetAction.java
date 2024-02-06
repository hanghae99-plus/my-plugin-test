package com.example.myplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

import java.util.HashMap;
import java.util.Map;

public class SnippetAction extends AnAction {
    // 스니펫을 저장할 맵. 키는 사용자가 입력할 문자열, 값은 대체될 스니펫
    private Map<String, String> snippetMap = new HashMap<>();

    public SnippetAction() {
        // 스니펫 맵에 "hi"라는 키와 대응되는 스니펫을 추가
        snippetMap.put("hi", "System.out.println(\"Hello World\");");

        // 기본 Spring Boot 애플리케이션 구조
        snippetMap.put("sba", "@SpringBootApplication\npublic class Application {\n\tpublic static void main(String[] args) {\n\t\tSpringApplication.run(Application.class, args);\n\t}\n}");

        // RESTful API 메소드 템플릿
        snippetMap.put("@c", "@RestController\npublic class MyController {\n\n\t@RequestMapping(\"/myEndpoint\")\n\tpublic String myMethod() {\n\t\treturn \"Hello World\";\n\t}\n}");

        // 서비스 계층 인터페이스와 구현
        snippetMap.put("@s", "@Service\npublic class MyService {\n\t@Autowired\n\tprivate MyRepository myRepository;\n}");

        // 데이터 접근 객체(DAO) 인터페이스
        snippetMap.put("dao", "public interface MyRepository extends JpaRepository<MyEntity, Long> {\n}");

        // 스프링부트 테스트 템플릿
        snippetMap.put("test@s", "@SpringBootTest\npublic class MyApplicationTests {\n\n\t@Test\n\tpublic void contextLoads() {\n\t}\n}");

        // mockito 테스트 템플릿
        snippetMap.put("test@m",
                "@ExtendWith(MockitoExtension.class)\n" +
                        "public class MyServiceTest {\n" +
                        "    @Mock\n" +
                        "    private MyDependency myDependency;\n\n" +
                        "    @InjectMocks\n" +
                        "    private MyService myService;\n\n" +
                        "    @Test\n" +
                        "    void testMyServiceMethod() {\n" +
                        "        // given\n" +
                        "        Mockito.when(myDependency.someMethod()).thenReturn(someValue);\n\n" +
                        "        // when\n" +
                        "        ResultType result = myService.testedMethod();\n\n" +
                        "        // then\n" +
                        "        Assertions.assertEquals(expectedValue, result);\n" +
                        "    }\n" +
                        "}");

        // application.properties 설정 스니펫
        snippetMap.put("ap", "spring.datasource.url=jdbc:mysql://localhost:3306/mydb\nspring.datasource.username=dbuser\nspring.datasource.password=dbpass");

        //application.yml 설정 스니펫
        snippetMap.put("yml", "server:\n  port: 8080\nspring:\n  config:\n    activate:\n      on-profile: dev\n  datasource:\n    url: jdbc:mysql://localhost:3306/mydb\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    username: user\n    password: pass");

        //gradle dependency
        // JUnit 테스트 관련 의존성
        snippetMap.put("gradleDependencyJUnit", "testImplementation 'org.springframework.boot:spring-boot-starter-test'");

        // 로그 관련 의존성
        snippetMap.put("gradleDependencyLogging", "implementation 'org.springframework.boot:spring-boot-starter-logging'");

        // JPA 의존성
        snippetMap.put("gradleDependencyJPA", "implementation 'org.springframework.boot:spring-boot-starter-data-jpa'");

        // 데이터베이스 의존성 (예시: H2)
        snippetMap.put("gradleDependencyH2", "runtimeOnly 'com.h2database:h2'");

        // Validation 의존성
        snippetMap.put("gradleDependencyValidation", "implementation 'org.springframework.boot:spring-boot-starter-validation'");

        // Jackson JSON 처리 의존성
        snippetMap.put("gradleDependencyJackson", "implementation 'com.fasterxml.jackson.core:jackson-databind'");

        // Lombok 의존성
        snippetMap.put("gradleDependencyLombok", "compileOnly 'org.projectlombok:lombok'\nannotationProcessor 'org.projectlombok:lombok'");

        // Spring Actuator 의존성
        snippetMap.put("gradleDependencyActuator", "implementation 'org.springframework.boot:spring-boot-starter-actuator'");

        // Cache 의존성
        snippetMap.put("gradleDependencyCache", "implementation 'org.springframework.boot:spring-boot-starter-cache'");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 현재 활성화된 에디터 로드
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();

        // 사용자가 선택한 텍스트 로드
        String selectedText = selectionModel.getSelectedText();

        // 선택한 텍스트가 스니펫 맵의 키에 해당한다면 스니펫으로 대체
        if (selectedText != null && snippetMap.containsKey(selectedText)) {
            String snippet = snippetMap.get(selectedText);
            int start = selectionModel.getSelectionStart();
            int end = selectionModel.getSelectionEnd();

            // WriteCommandAction을 사용하여 문서를 수정
            WriteCommandAction.runWriteCommandAction(e.getProject(), () ->
                    document.replaceString(start, end, snippet)
            );
        }
    }
}

