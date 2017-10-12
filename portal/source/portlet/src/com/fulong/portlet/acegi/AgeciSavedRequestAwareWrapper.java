package com.fulong.portlet.acegi;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.util.PortResolver;
import org.springframework.security.wrapper.SavedRequestAwareWrapper;

/**
 * 覆盖实现AgeciSavedRequestAwareWrapper
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class AgeciSavedRequestAwareWrapper extends SavedRequestAwareWrapper {
    //~ Instance fields ================================================================================================

      private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

      /**
       * The prefix passed by the filter. It will be prepended to any supplied role values before
       * comparing it with the roles obtained from the security context.
       */
      private String rolePrefix;

      //~ Constructors ===================================================================================================

      public AgeciSavedRequestAwareWrapper(
              HttpServletRequest request,
              PortResolver portResolver,
              String rolePrefix) {
          super(request,portResolver,rolePrefix);
          this.rolePrefix = rolePrefix;
      }

      //~ Methods ========================================================================================================

      /**
       * Obtain the current active <code>Authentication</code>
       *
       * @return the authentication object or <code>null</code>
       */
      private Authentication getAuthentication() {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();

          if (!authenticationTrustResolver.isAnonymous(auth)) {
              return auth;
          }

          return null;
      }

      /**
       * Returns the principal's name, as obtained from the <code>SecurityContextHolder</code>. Properly handles
       * both <code>String</code>-based and <code>UserDetails</code>-based principals.
       *
       * @return the username or <code>null</code> if unavailable
       */
      public String getRemoteUser() {
          Authentication auth = getAuthentication();

          if ((auth == null) || (auth.getPrincipal() == null)) {
              return null;
          }

          if (auth.getPrincipal() instanceof UserDetails) {
              return ((UserDetails) auth.getPrincipal()).getUsername();
          }

          return auth.getPrincipal().toString();
      }

      /**
       * Returns the <code>Authentication</code> (which is a subclass of <code>Principal</code>), or
       * <code>null</code> if unavailable.
       *
       * @return the <code>Authentication</code>, or <code>null</code>
       */
      public Principal getUserPrincipal() {
          Authentication auth = getAuthentication();

          if ((auth == null) || (auth.getPrincipal() == null)) {
              return null;
          }

          return (Principal)auth.getPrincipal();
      }

      private boolean isGranted(String role) {
          Authentication auth = getAuthentication();

          if( rolePrefix != null ) {
              role = rolePrefix + role;
          }

          if ((auth == null) || (auth.getPrincipal() == null) || (auth.getAuthorities() == null)) {
              return false;
          }

          for (int i = 0; i < auth.getAuthorities().length; i++) {
              if (role.equals(auth.getAuthorities()[i].getAuthority())) {
                  return true;
              }
          }

          return false;
      }

      /**
       * Simple searches for an exactly matching {@link org.springframework.security.GrantedAuthority#getAuthority()}.<p>Will
       * always return <code>false</code> if the <code>SecurityContextHolder</code> contains an
       * <code>Authentication</code> with <code>null</code><code>principal</code> and/or <code>GrantedAuthority[]</code>
       * objects.</p>
       *
       * @param role the <code>GrantedAuthority</code><code>String</code> representation to check for
       *
       * @return <code>true</code> if an <b>exact</b> (case sensitive) matching granted authority is located,
       *         <code>false</code> otherwise
       */
      public boolean isUserInRole(String role) {
          return isGranted(role);
    }}
