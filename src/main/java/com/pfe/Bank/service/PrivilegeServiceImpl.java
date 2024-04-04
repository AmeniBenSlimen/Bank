package com.pfe.Bank.service;

import com.pfe.Bank.exception.DuplicateEntity;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.model.*;
import com.pfe.Bank.repository.MenuRepository;
import com.pfe.Bank.repository.ModuleRepository;
import com.pfe.Bank.repository.PrivilegeRepository;
import com.pfe.Bank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{
    @Autowired
    RoleRepository ntRoleRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public Role findByName(ERole name) throws MissingEntity {
        Optional<Role> optional = roleRepository.findByName(name);
        if(!optional.isPresent()){
            throw new MissingEntity("RoleName not found with Name : "+name);
        }
        return optional.get();
    }

    @Override
    public Menu findByCodmenu(String codMenu) throws MissingEntity {
        Optional<Menu> menue = menuRepository.findByCodmenu(codMenu);
        if(!menue.isPresent()){
            throw new MissingEntity("Menue not found with code Menu : "+codMenu);
        }
        return menue.get();
    }
    @Override
    public Privilege addPrivilege(PrivilegeForm form) throws MissingEntity {
        Role role =findByName(ERole.valueOf(form.getCdRole()));
        Menu menu =findByCodmenu(form.getCdMenu());
        Privilege privilege = new Privilege();
        privilege.setRole(role);
        privilege.setMenu(menu);
        return privilegeRepository.save(privilege);
    }
    //cette methode permet de réqupérer tous les roles et et tous les modules w tkhabyhom f west privilege =form
    //yaany bch traj3elna form fyh les roles et les modules
    @Override
    public PrivilegeForm displayForm() throws DuplicateEntity {
        List<Role> roles = ntRoleRepository.findAll();
        //List<Module> modules = moduleRepository.findAll();
        PrivilegeForm form = new PrivilegeForm();
        form.setRoles(roles);
        //form.setModules(modules);
        return form;
    }
   /* @Override
    public PrivilegeForm displayMenuForm(PrivilegeForm privilegeForm) {
        Modul module = moduleRepository.findById(privilegeForm.getModules()).orElse(null);
        List<Menu> menus = menuRepository.findByModule(module);
        privilegeForm.setMenus(menus);
        return privilegeForm;
    }*/
   /*@Override
    public PrivilegeForm displayMenuForm(PrivilegeForm privilegeForm) {
        List<Modul> modules = moduleRepository.getModules(privilegeForm.getModules());
        if (!modules.isEmpty()) {
            Modul module = modules.get(0); // Récupérer le premier module trouvé
            List<Menu> menus = menuRepository.findByModule(module);
            privilegeForm.setMenus(menus);
        } else {
            // Gérer le cas où aucun module n'est trouvé (la liste est vide)
            // Par exemple, afficher un message d'erreur ou effectuer une autre action appropriée
        }
        return privilegeForm;
    }

    /*
    @Override
    public void addPrivilege(long roleId, String menuId) {

    }

    @Override
    public void deletePrivilege(long roleId, String menuId) {

    }

    @Override
    public void setPrivilegeByRole(PrivilegeForm privilegeForm) {

        List<String> codeMenus = privilegeForm.getCodeMenus();

        List<Menu> menus = codeMenus.stream().map(codeMenu ->
                menuRepository.findByCodmenu(codeMenu).get()).collect(java.util.stream.Collectors.toList());

        Role role = roleRepository.findById(privilegeForm.getRole()).get();

        menus.forEach(menu -> {
            Privilege privilege = new Privilege() ;
            privilege.setMenu(menu);
            privilege.setRole(role);
            privilegeRepository.save(privilege);
        });

    }

    @Override
    public List<Privilege> getPrivilegeByRole(long roleId) {
        Role role = roleRepository.findById(roleId).get();
        List<Privilege> privileges = privilegeRepository.findByRole(role);
        return privileges;
    }

    @Override
    public long removegrant(long privilegeId) {
        Privilege p = privilegeRepository.findById(privilegeId).get();
        privilegeRepository.delete(p);
        return p.getRole().getId();
    }

    */
}

