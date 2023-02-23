package id.maseka.playground.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.snippet.Attributes.key;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
abstract public class BaseControllerTest {

    public MockMvc mockMvc;
    public final ObjectMapper objectMapper = new ObjectMapper();

    private Map<Class<?>, ConstraintDescriptions> constraints = initConstraint();  // map constraints if its model properties

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    protected abstract Collection<Class<?>> defineModelToAssignConstraint();

    private Map<Class<?>, ConstraintDescriptions> initConstraint() {
        var models = defineModelToAssignConstraint();
        return models.stream().collect(Collectors.toMap(e -> e, ConstraintDescriptions::new));
    }

    public Attributes.Attribute[] getConstraintAtributesOfProperty(Class<?> clazz, String property) {
        return constraints.get(clazz).descriptionsForProperty(property).stream().map(e -> key("constraints").value(e)).toArray(Attributes.Attribute[]::new);
    }

}
