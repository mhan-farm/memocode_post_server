package io.mhan.springjpatest2.base.requestscope;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Objects;
import java.util.UUID;

@Component
@RequestScope
public class SecurityUser {

    private OAuth2AuthenticatedPrincipal principal;

    public SecurityUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            this.principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        }
    }

    public UUID getId() {
        return isAnonymous() ?
                null
                :
                UUID.fromString(Objects.requireNonNull(this.principal.getAttribute("sub")));
    }

    public boolean isAnonymous() {
        return principal == null;
    }

    public boolean isUser() {
        return principal.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SCOPE_role:user"));
    }

    public boolean isAdmin() {
        return principal.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SCOPE_role:admin"));
    }

}
