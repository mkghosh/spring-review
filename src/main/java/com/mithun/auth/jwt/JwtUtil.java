//package com.mithun.auth.jwt;
//
//import com.mithun.db.mysql.entity.RestUser;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//public class JwtUtil {
//
//    /**
//     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
//     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
//     * 
//     * @param token the JWT token to parse
//     * @return the User object extracted from specified token or null if a token is invalid.
//     */
//    public RestUser parseToken(String token) {
//        try {
//            Claims body = Jwts.parser()
//                    .setSigningKey(JWTConstants.KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            RestUser u = new RestUser();
//            u.setUserName(body.getSubject());
//            u.setId(Long.parseLong((String) body.get("userId")));
////            u.setUserRoles(new ArrayList<UserRole> body.get("role"));
//
//            return u;
//
//        } catch (JwtException | ClassCastException e) {
//            return null;
//        }
//    }
//
//    /**
//     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
//     * User object. Tokens validity is infinite.
//     * 
//     * @param u the user for which the token will be generated
//     * @return the JWT token
//     */
//    public String generateToken(RestUser u) {
//        Claims claims = Jwts.claims().setSubject(u.getUserName());
//        claims.put("userId", u.getId() + "");
//        claims.put("role", u.getUserRoles());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, JWTConstants.KEY)
//                .compact();
//    }
//}