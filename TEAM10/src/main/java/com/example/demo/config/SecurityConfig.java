package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.mapper.ProjectUser;
import com.example.demo.mapper.ProjectUserMapper;

@Configuration
public class SecurityConfig {

    // @Bean
    @SuppressWarnings("deprecation")
	@Bean
    public PasswordEncoder passwordEncoder() {
        // 平文パスワードをそのまま比較（開発・検証用）。本番では使用しないでください。
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Autowired
    private ProjectUserMapper mapper;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 開発用にCSRFを無効化（必要に応じて有効化し、フォームにトークンを追加）
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/sign_up", "/error", "/h2-console/**", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", false)
                // 保存されたリクエストを優先（常に"/"へ強制しない）
                //ログイン成功時のカスタム処理セッションのメール登録
                .successHandler((request, response, authentication)->{
                	//ログイン成功時にメールをセッションに保存
//                	String username = authentication.getName();
//                	ProjectUser user = mapper.findByUsername(username);
                	String email = request.getParameter("email");
                	ProjectUser user = mapper.findByUsername(email);
                	
                	if(user!=null && user.getEmail()!=null) {
                		request.getSession().setAttribute("email", user.getEmail());
                	}
                	//デフォルトの動作を維持（保存されたリクエストへ遷移）
                	response.sendRedirect(request.getContextPath()+"/");
                })
                
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

}
