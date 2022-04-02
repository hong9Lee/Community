package web.community.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableCaching
public class JpaConfiguration {

    @Bean
    @Scope("prototype")
    public EhCacheCacheManager getEhCacheManager(){
        return new EhCacheCacheManager(getEhCacheFactory().getObject());
    }

    @Bean
    @Scope("prototype")
    public EhCacheManagerFactoryBean getEhCacheFactory(){
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }

}
