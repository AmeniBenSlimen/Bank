package com.pfe.Bank.service;

import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.model.Modul;
import com.pfe.Bank.model.Role;
import com.pfe.Bank.repository.MenuRepository;
import com.pfe.Bank.repository.ModuleRepository;
import com.pfe.Bank.repository.PrivilegeRepository;
import com.pfe.Bank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //cette methode permet de réqupérer tous les roles et et tous les modules w tkhabyhom f west privilege =form
    //yaany bch traj3elna form fyh les roles et les modules
    @Override
    public PrivilegeForm displayForm() {
        List<Role> roles = ntRoleRepository.findAll();
        List<Modul> modules = moduleRepository.findAll();
        PrivilegeForm form = new PrivilegeForm();
        form.setRoles(roles);
        form.setModules(modules);
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

