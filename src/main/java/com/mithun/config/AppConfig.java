package com.mithun.config;

import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mithun.db.mysql.service.UserService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.mithun")
public class AppConfig implements WebMvcConfigurer {
	
	@Resource
	private ApplicationContext applicationContext;

	@Autowired
	private UserService userService;
	
//	@Autowired
//	private SpringSecurityDialect springSecurityDialect;
	
//	@Autowired
//	private Environment env;
	
//	//set up a logger
	private Logger logger = Logger.getLogger(getClass().getName());

//	@Bean
//	public ThymeleafViewResolver viewResolver(){
//	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
////	    viewResolver.setTemplateEngine(templateEngine());
//	    // NOTE 'order' and 'viewNames' are optional
//	    viewResolver.setOrder(1);
//	    viewResolver.setViewNames(new String[] {".html", ".xhtml"});
//	    return viewResolver;
//	}
//	
//	@Bean
//	public SpringResourceTemplateResolver templateResolver(){
//	    // SpringResourceTemplateResolver automatically integrates with Spring's own
//	    // resource resolution infrastructure, which is highly recommended.
//	    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//	    templateResolver.setApplicationContext(this.applicationContext);
//	    templateResolver.setPrefix("classpath:/templates/");
//	    templateResolver.setSuffix(".html");
//	    // HTML is the default value, added here for the sake of clarity.
//	    templateResolver.setTemplateMode(TemplateMode.HTML);
//	    // Template cache is true by default. Set to false if you want
//	    // templates to be automatically updated when modified.
//	    templateResolver.setCacheable(false);
//	    return templateResolver;
//	}

//	@Bean
//	public SpringTemplateEngine templateEngine(){
//	    // SpringTemplateEngine automatically applies SpringStandardDialect and
//	    // enables Spring's own MessageSource message resolution mechanisms.
//	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//	    templateEngine.setTemplateResolver(templateResolver());
//	    
//	    //Add spring security dialect to the template engine to have access to the 
//	    //sec tag in thymeleaf.
//	    templateEngine.addDialect(springSecurityDialect());
//	    // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
//	    // speed up execution in most scenarios, but might be incompatible
//	    // with specific cases when expressions in one template are reused
//	    // across different data types, so this flag is "false" by default
//	    // for safer backwards compatibility.
//	    templateEngine.setEnableSpringELCompiler(true);
//	    
//	    return templateEngine;
//	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
            "/webjars/**",
            "/image/**",
            "/css/**",
            "/js/**")
            .addResourceLocations(
                    "classpath:/META-INF/resources/webjars/",
                    "classpath:/static/image/",
                    "classpath:/static/css/",
                    "classpath:/static/js/");
     }

	
//	//Thymeleaf Configuration
//	@Bean
//    public SpringSecurityDialect springSecurityDialect(){
//        return new SpringSecurityDialect();
//    }
}








