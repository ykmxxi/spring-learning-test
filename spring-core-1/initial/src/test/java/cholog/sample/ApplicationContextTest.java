package cholog.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

class ApplicationContextTest {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextTest.class);

    @Test
    void test1() {
        // IoC 컨테이너
        // StaticApplicationContext는 직접 Bean을 등록할 떄 보통 사용, 설정 정보를 사용하지 않음
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerSingleton("hello", Hello.class);

        Hello helloBean = ac.getBean("hello", Hello.class);
        assertThat(helloBean).isNotNull();
        assertThat(helloBean.name).isNull();
        assertThat(helloBean.printer).isNull();
        log.info("helloBean name = " + helloBean.name);
    }

    @Test
    void test2() {
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1).isNotNull();

        // 빈 메타정보를 담을 오브젝트를 생성
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);

        // 빈 프로퍼티 설정
        // 없는 프로퍼티를 넣어도 예외는 터지지 않음 -> 이때는 그냥 PropertyValues 라는 곳에 key-value 형태로 저장
//        helloDef.getPropertyValues().addPropertyValue("none", "none");
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new Printer());

        // 이때 없는 프로퍼티가 있으면(클래스 설정에 필요없는 프로퍼티) 예외 터짐
        ac.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = ac.getBean("hello2", Hello.class);
        assertThat(hello2).isNotNull();
        assertThat(hello2.sayHello()).isEqualTo("Hello Spring");
        assertThat(hello2.name).isEqualTo("Spring");
        assertThat(hello2.printer).isNotNull();

        assertThat(hello1).isNotSameAs(hello2);
        assertThat(ac.getBeanFactory().getBeanDefinitionCount()).isEqualTo(2);
    }

    @Test
    void test3() {
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerBeanDefinition("printer", new RootBeanDefinition(Printer.class));

        RootBeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");

        // 프로퍼티 네임이 printer인 것을 찾아서 DI
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
        ac.registerBeanDefinition("hello", helloDef);

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertThat(ac.getBean("printer").toString()).isEqualTo("Hello Spring");
    }
}
