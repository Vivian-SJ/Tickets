//package tickets.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import tickets.service.impl.MemberServiceImpl;
//
//@Configuration
//@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)//允许进入页面方法前检验
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    UserDetailsService customUserService() {
//        return new MemberServiceImpl();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService());
//    }
//
//    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests()
////                .anyRequest().permitAll()       // 允许所有请求通过
////                .and()
////                .csrf()
////                .disable()                      // 禁用 Spring Security 自带的跨域处理
////                .sessionManagement()                        // 定制我们自己的 session 策略
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session
////    }
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.POST, "/tickets/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                // We filter the api/login requests
//                .addFilterBefore(new JWTLoginFilter("/tickets/login", authenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class)
//                // And filter other requests to check the presence of JWT in header
//                .addFilterBefore(new JWTAuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class);
//    }
//}
