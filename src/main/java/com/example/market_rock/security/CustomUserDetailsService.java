package com.example.market_rock.security;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final Firestore db;
    public CustomUserDetailsService(Firestore db){
        this.db = db;
    }

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
      try{
       DocumentSnapshot data = db.collection("user").document(uid).get().get();
          if (!data.exists()) {
              throw new UsernameNotFoundException("User not found");
          }
          System.out.println(data.get("role") +" "+ data.get("email"));
          return new CustomUserDetails(
                  data.getId(),
                  data.getString("email"),
                  data.getString("role")
          );
      }catch (Exception e){
          throw new UsernameNotFoundException("user not found");
      }
    }
}
