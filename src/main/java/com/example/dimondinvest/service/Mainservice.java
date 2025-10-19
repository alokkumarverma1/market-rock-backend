package com.example.dimondinvest.service;

import com.example.dimondinvest.JwtService.JwtService;
import com.example.dimondinvest.dto.*;
import com.example.dimondinvest.entity.*;
import com.example.dimondinvest.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Mainservice {


    @Autowired
    private Registerrepo registerrepo;
    @Autowired
    private Rolesrepo rolesrepo;
    @Autowired
    private Objectres objectres;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authmanager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private Dntyperepo dntyperepo;
    @Autowired
    private Dnewsrepo dnewsrepo;
    @Autowired
    private Savepostrepo savepostrepo;
    @Autowired
    private Dnewsdetailrepo dnewsdetailrepo;
    @Autowired
    private Dnewslinesrepo dnewslinesrepo;
    @Autowired
    private EmailService emailService;


    @Value("${spring.mail.username}")
    private String from;



    // register user api
    public String userregister(Registerdto registerdto){
        Register registers = registerrepo.findByNumber(registerdto.getNumber());
        if(registers !=  null){
            return "Number already registered, try again";
        }
        Register register = new Register();
        register.setName(registerdto.getName());
        register.setNumber(registerdto.getNumber());
        register.setEmail(registerdto.getEmail().trim()); // trim extra spaces
        register.setCity(registerdto.getCity());
        Roles roles;
        if(registerdto.getNumber() == 8172984928L){
            roles = rolesrepo.findByRolename("admin");
            if(roles == null){
                roles = new Roles();
                roles.setRolename("admin");
                rolesrepo.save(roles);
            }
        } else {
            roles = rolesrepo.findByRolename("user");
            if(roles == null){
                roles = new Roles();
                roles.setRolename("user");
                rolesrepo.save(roles);
            }
        }
        register.setRoles(roles);
        register.setPassword(passwordEncoder.encode(registerdto.getPassword()));

        String sub = "registration";
        String mes = "your registraction sucess";
        System.out.println( "to email :" + registerdto.getEmail());
        System.out.println("from : marketrockofficial@gmail.com");
        emailService.sendmail(registerdto.getEmail(),sub,mes,from);
        registerrepo.save(register);
         return "User register success";
    }



    // this is user login page
    public ResponseEntity<Map<String, Object>> userlogin(Registerdto registerdto) {
        Map<String, Object> res = new HashMap<>();
        Register register = registerrepo.findByNumber(registerdto.getNumber());
        if (register == null) {
            res.put("message", "Number not registered");
            return ResponseEntity.badRequest().body(res);
        }
        if (!passwordEncoder.matches(registerdto.getPassword(), register.getPassword())) {
            res.put("message", "Password Not Match");
            return ResponseEntity.badRequest().body(res);
        }
        Authentication authentication = authmanager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerdto.getNumber(),
                        registerdto.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            String role = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("");
            String token = jwtService.generateToken(registerdto.getNumber(), role);
            Map<String, Object> responce = new HashMap<>();
            responce.put("token", token);
            responce.put("message", "Login successful");
            return ResponseEntity.ok(responce);
        }
        Map<String, Object> responce1 = new HashMap<>();
        Objectres objectres1 = new Objectres();
        objectres1.setMessage("failed login");
        responce1.put("message", objectres1);
        return ResponseEntity.status(401).body(responce1);
    }


    public String dimondsnews(Dnewsdto dnewsdto, Authentication authentication) {
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        System.out.println(dnewsdto);
        // dnews type
        Dntype dntype = dntyperepo.findByTypename(dnewsdto.getTypename());
        if (dntype == null) {
            dntype = new Dntype();
            dntype.setTypename(dnewsdto.getTypename());
            dntype = dntyperepo.save(dntype);
        }
        // create Dnews
        Dnews dnews1 = new Dnews();
        dnews1.setTime(dnewsdto.getTime());
        dnews1.setTitle(dnewsdto.getTitle());
        dnews1.setCompnayname(dnewsdto.getCompnayname());
        dnews1.setDate(dnewsdto.getDate());
        dnews1.setDntype(dntype);
        // news details
        List<Dnewsdetail> dnewsdetails = dnewsdto.getDnewsdetaildtos().stream().map(d -> {
            Dnewsdetail dnewsdetail = new Dnewsdetail();
            dnewsdetail.setHeading(d.getHeading());
            dnewsdetail.setDnews(dnews1);

            List<Dnewslines> dnewslines = d.getDnewslinedto().stream().map(r -> {
                Dnewslines dnewslines1 = new Dnewslines();
                dnewslines1.setNews(r.getDnewslines());
                dnewslines1.setDnewsdetail(dnewsdetail);
                return dnewslines1;
            }).toList();

            dnewsdetail.setDnewslines(dnewslines);
            return dnewsdetail;
        }).toList();

        dnews1.setDnewsdetails(dnewsdetails);

        // add Dnews to Dntype
        List<Dnews> dnewsList = dntype.getDnewsList();
        if (dnewsList == null) {
            dnewsList = new ArrayList<>();
        }
        dnewsList.add(dnews1);
        dntype.setDnewsList(dnewsList);

        // save
        dntyperepo.save(dntype);
        return "News added successfully";
    }

    // alltradingnews
    public ResponseEntity<?> alltradingnews(){
        Dntype dntype = dntyperepo.findByTypename("trading");
        if (dntype == null) {
            return ResponseEntity.badRequest().body("Trading type not found");
        }
        List<Dnews> dnews = dnewsrepo.findAllByDntypeId(dntype.getId());
        if(dnews.isEmpty()){
            return ResponseEntity.badRequest().body("post not found");
        }
        List<Dnewsdto> dnewsdtos = dnews.stream().map(d -> {
            Dnewsdto dnewsdto = new Dnewsdto();
            dnewsdto.setTime(d.getTime());
            dnewsdto.setTitle(d.getTitle());
            dnewsdto.setCompnayname(d.getCompnayname());
            dnewsdto.setDate(d.getDate());
            dnewsdto.setId(d.getId());

            List<Dnewsdetaildto> dnewsdetailsdto = d.getDnewsdetails().stream().map(g->{
                Dnewsdetaildto dnewsdetaildto = new Dnewsdetaildto();
                dnewsdetaildto.setHeading(g.getHeading());

              List<Dnewslinedto> dnewslinedtos = g.getDnewslines().stream().map(f->{
                  Dnewslinedto dnewslinedto = new Dnewslinedto();
                  dnewslinedto.setDnewslines(f.getNews());
                  return dnewslinedto;
              }).toList();
              dnewsdetaildto.setDnewslinedto(dnewslinedtos);
                return dnewsdetaildto;
            }).toList();
            dnewsdto.setDnewsdetaildtos(dnewsdetailsdto);
            return dnewsdto;
        }).toList();

        return ResponseEntity.ok().body(dnewsdtos);
    }

//
//
//    // all invest news
    public ResponseEntity<?> allinvestnews(){
        Dntype dntype = dntyperepo.findByTypename("invest");
        if (dntype == null) {
            return ResponseEntity.badRequest().body("Trading type not found");
        }
        List<Dnews> dnews = dnewsrepo.findAllByDntypeId(dntype.getId());
        if(dnews.isEmpty()){
            return ResponseEntity.badRequest().body("post not found");
        }
        List<Dnewsdto> dnewsdtos = dnews.stream().map(d -> {
            Dnewsdto dnewsdto = new Dnewsdto();
            dnewsdto.setTime(d.getTime());
            dnewsdto.setTitle(d.getTitle());
            dnewsdto.setCompnayname(d.getCompnayname());
            dnewsdto.setDate(d.getDate());
            dnewsdto.setId(d.getId());

            List<Dnewsdetaildto> dnewsdetailsdto = d.getDnewsdetails().stream().map(g->{
                Dnewsdetaildto dnewsdetaildto = new Dnewsdetaildto();
                dnewsdetaildto.setHeading(g.getHeading());

                List<Dnewslinedto> dnewslinedtos = g.getDnewslines().stream().map(f->{
                    Dnewslinedto dnewslinedto = new Dnewslinedto();
                    dnewslinedto.setDnewslines(f.getNews());
                    return dnewslinedto;
                }).toList();
                dnewsdetaildto.setDnewslinedto(dnewslinedtos);
                return dnewsdetaildto;
            }).toList();
            dnewsdto.setDnewsdetaildtos(dnewsdetailsdto);
            return dnewsdto;
        }).toList();

        return ResponseEntity.ok().body(dnewsdtos);
    }
//
//
//    // all prime news
    public ResponseEntity<?> allprimenews(){
        Dntype dntype = dntyperepo.findByTypename("prime");
        if (dntype == null) {
            return ResponseEntity.badRequest().body("Trading type not found");
        }
        List<Dnews> dnews = dnewsrepo.findAllByDntypeId(dntype.getId());
        if(dnews.isEmpty()){
            return ResponseEntity.badRequest().body("post not found");
        }
        List<Dnewsdto> dnewsdtos = dnews.stream().map(d -> {
            Dnewsdto dnewsdto = new Dnewsdto();
            dnewsdto.setTime(d.getTime());
            dnewsdto.setTitle(d.getTitle());
            dnewsdto.setCompnayname(d.getCompnayname());
            dnewsdto.setDate(d.getDate());
            dnewsdto.setId(d.getId());

            List<Dnewsdetaildto> dnewsdetailsdto = d.getDnewsdetails().stream().map(g->{
                Dnewsdetaildto dnewsdetaildto = new Dnewsdetaildto();
                dnewsdetaildto.setHeading(g.getHeading());

                List<Dnewslinedto> dnewslinedtos = g.getDnewslines().stream().map(f->{
                    Dnewslinedto dnewslinedto = new Dnewslinedto();
                    dnewslinedto.setDnewslines(f.getNews());
                    return dnewslinedto;
                }).toList();
                dnewsdetaildto.setDnewslinedto(dnewslinedtos);
                return dnewsdetaildto;
            }).toList();
            dnewsdto.setDnewsdetaildtos(dnewsdetailsdto);
            return dnewsdto;
        }).toList();

        return ResponseEntity.ok().body(dnewsdtos);
    }


    // save posts

    public String saveposts(Dnewsdto dnewsdto , Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        System.out.println(dnewsdto);
        List<Savepost> saveposts = new ArrayList<>();
        Savepost savepost = new Savepost();
           savepost.setTitle(dnewsdto.getTitle());
           savepost.setTime(dnewsdto.getTime());
           savepost.setDate(dnewsdto.getDate());
           savepost.setCompnayname(dnewsdto.getCompnayname());
          savepost.setPostid(dnewsdto.getPostid());
           savepost.setRegister(register);
    // save post area
           List<Savepostdetail> savepostdetails = dnewsdto.getDnewsdetaildtos().stream().map(d->{
               Savepostdetail savepostdetail = new Savepostdetail();
               savepostdetail.setSavepost(savepost);
               savepostdetail.setHeading(d.getHeading());

             List<SavePostLine> savePostLines = d.getDnewslinedto().stream().map(k->{
                 SavePostLine savePostLine = new SavePostLine();
                 savePostLine.setSavepostdetail(savepostdetail);
                 savePostLine.setNews(k.getDnewslines());
                 return savePostLine;
             }).collect(Collectors.toList());
               savepostdetail.setSavePostLines(savePostLines);
             return savepostdetail;
           }).collect(Collectors.toList());

           savepost.setSavepostdetails(savepostdetails);
            savepostrepo.save(savepost);
            saveposts.add(savepost);
            register.setSaveposts(saveposts);
           registerrepo.save(register);
          return "post save sucess";
    }

    public ResponseEntity<?> allsavepost(Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        List<Savepost> savepost = savepostrepo.findAllByRegisterId(register.getId());
        if(savepost == null){
            return ResponseEntity.ok("no post available");
        }
        List<Dnewsdto> dnewsdtos = savepost.stream().map(d -> {
            Dnewsdto dnewsdto = new Dnewsdto();
            dnewsdto.setTime(d.getTime());
            dnewsdto.setTitle(d.getTitle());
            dnewsdto.setCompnayname(d.getCompnayname());
            dnewsdto.setDate(d.getDate());
            dnewsdto.setPostid(d.getPostid());

            List<Dnewsdetaildto> dnewsdetailsdto = d.getSavepostdetails().stream().map(g->{
                Dnewsdetaildto dnewsdetaildto = new Dnewsdetaildto();
                dnewsdetaildto.setHeading(g.getHeading());

                List<Dnewslinedto> dnewslinedtos = g.getSavePostLines().stream().map(f->{
                    Dnewslinedto dnewslinedto = new Dnewslinedto();
                    dnewslinedto.setDnewslines(f.getNews());
                    return dnewslinedto;
                }).collect(Collectors.toList());
                dnewsdetaildto.setDnewslinedto(dnewslinedtos);
                return dnewsdetaildto;
            }).collect(Collectors.toList());
            dnewsdto.setDnewsdetaildtos(dnewsdetailsdto);
            return dnewsdto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dnewsdtos);
    }
    // unsave post

    public String unsavepost(Dnewsdto dnewsdto, Authentication authentication) {
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        System.out.println(dnewsdto.getPostid());

        List<Savepost> savepostList = savepostrepo.findAllByRegisterId(register.getId());

        for (Savepost s : savepostList) {
            if (Objects.equals(s.getPostid(), dnewsdto.getPostid())) {
                savepostrepo.delete(s);
                return "unsave post success ";
            }
        }
        return "post not found ";
    }



    // delete post
    @Transactional
    public String deletepost(Dnewsdto dnewsdto, Authentication authentication) {
        Long postid = dnewsdto.getPostid();
        if (postid == null) {
            return "Post ID is missing ";
        }

        // Check if post exists
        if (!dnewsrepo.existsById(postid)) {
            return "Post not found ";
        }
        dnewsrepo.deleteById(postid);
        return "Post deleted successfully ";
    }

    // next version
   public ResponseEntity<?> updateprofile(Registerdto registerdto, Authentication authentication){
        Register register = registerrepo.findByNumber(Long.parseLong(authentication.getName()));
        register.setName(registerdto.getName());
        register.setCity(registerdto.getCity());
        register.setEmail(registerdto.getEmail());
        register.setPassword(registerdto.getPassword());
        register.setNumber(registerdto.getNewnumber());
        registerrepo.save(register);
        return ResponseEntity.ok("update success");
   }

   // delte profile
   @Transactional
   public ResponseEntity<?> deleteprofile(Authentication authentication){
       Long number = Long.parseLong(authentication.getName());

       Register register = registerrepo.findByNumber(number);
       if(register == null){
           return ResponseEntity.badRequest().body("user not found");
       }

      registerrepo.deleteById(register.getId());

       return ResponseEntity.ok("delete success");
   }


}
