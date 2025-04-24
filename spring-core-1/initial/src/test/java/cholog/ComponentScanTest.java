package cholog;

import static cholog.utils.ContextUtils.getApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import cholog.scan.ComponentScanBean;
import cholog.scan.ContextConfiguration;

class ComponentScanTest {

    @Test
    void scanComponent() {
        ApplicationContext context = getApplicationContext(ContextConfiguration.class);
        ComponentScanBean componentScanBean = context.getBean("componentScanBean", ComponentScanBean.class);

        assertThat(componentScanBean).isNotNull();
    }
}
