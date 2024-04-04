package com.pfe.Bank.service;

import com.pfe.Bank.exception.DuplicateEntity;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.model.*;

import java.nio.charset.MalformedInputException;

public interface PrivilegeService {
    Role findByName(ERole name) throws MissingEntity;
    public Menu findByCodmenu(String codMenu) throws MissingEntity;

    public Privilege addPrivilege(PrivilegeForm form) throws MissingEntity;
    PrivilegeForm displayForm() throws DuplicateEntity;

    //PrivilegeForm displayMenuForm(PrivilegeForm privilegeForm);

   /* void addPrivilege(long roleId, String menuId) ;
    void deletePrivilege(long roleId, String menuId) ;
    void setPrivilegeByRole(PrivilegeForm privilegeForm) ;

    List<Privilege> getPrivilegeByRole(long roleId) ;

    long removegrant(long privilegeId) ;*/
}
