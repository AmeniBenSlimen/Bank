package com.pfe.Bank.service;

import com.pfe.Bank.exception.DuplicateEntity;
import com.pfe.Bank.exception.MissingEntity;
import com.pfe.Bank.form.PrivilegeForm;
import com.pfe.Bank.model.*;

import java.nio.charset.MalformedInputException;
import java.util.List;

public interface PrivilegeService {
    Privilege addPrivilege(long roleId, String menuId) ;

    void deletePrivilege(long id);
    //List<Privilege> getPrivilegeByRole(long roleId) ;
    List<Privilege> getAllPrivileges() ;
    Privilege getPrivilegeById(long id);
    Privilege updatePrivilege(long id, Long roleId, String menuId);
    List<Privilege> getPrivilegesByRole(long roleId);


    //PrivilegeForm displayMenuForm(PrivilegeForm privilegeForm);

   /* void addPrivilege(long roleId, String menuId) ;
    void deletePrivilege(long roleId, String menuId) ;
    void setPrivilegeByRole(PrivilegeForm privilegeForm) ;

    List<Privilege> getPrivilegeByRole(long roleId) ;

    long removegrant(long privilegeId) ;*/
}
