package com.SpringProject.core.controllers;

/*
@Controller
@RequiredArgsConstructor
public class controller2 {

  private final qweRepos qweRepos;

  public List<Message> listProd(String a) {
    //List<Message> prod = qweRepos.findAll();
    qweRepos.findByTitle(a);
    return qweRepos.findAll();
  }
} */
/*
  public controller2(com.SpringProject.core.repos.qweRepos qweRepos) {
    this.qweRepos = qweRepos;
  }
  @GetMapping
  public List<Message> List(){
    return qweRepos.findAll();
  }

  @GetMapping("{id}")
  public Message getone(@PathVariable("id") Message message){
    return message;
  }
  @PostMapping
  public Message create(@RequestBody Message message){
    return qweRepos.save(message);
  }

  @PutMapping("id")
  public Message update(@PathVariable("id") Message messageFromDb,
      @RequestBody Message message ){
    BeanUtils.copyProperties(message, messageFromDb, "id");
    return qweRepos.save(message);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") Message message){
    qweRepos.delete(message);
  }*/
