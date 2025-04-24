package cholog.di;

import org.springframework.stereotype.Service;

@Service
public class ConstructorInjection {

    private final InjectionBean injectionBean;

    //    @Autowired
    public ConstructorInjection(final InjectionBean injectionBean) {
        this.injectionBean = injectionBean;
    }

    public String sayHello() {
        return injectionBean.hello();
    }
}
