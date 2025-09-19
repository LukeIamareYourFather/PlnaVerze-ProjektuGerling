package com.danger.insurance.accounts.models.dto.mappers;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.dto.get.AccountCreateDTO;
import com.danger.insurance.accounts.models.dto.get.AccountDetailsDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-19T18:12:43+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class AccountsMapperImpl implements AccountsMapper {

    @Override
    public AccountsEntity accountsDTOToEntity(AccountsDTO source) {
        if ( source == null ) {
            return null;
        }

        AccountsEntity accountsEntity = new AccountsEntity();

        accountsEntity.setAccountId( source.getAccountId() );
        accountsEntity.setUserEmail( source.getUserEmail() );
        accountsEntity.setUserPassword( source.getUserPassword() );
        accountsEntity.setUserRole( source.getUserRole() );

        return accountsEntity;
    }

    @Override
    public AccountsDTO accountsEntityToAccountsDTO(AccountsEntity source) {
        if ( source == null ) {
            return null;
        }

        AccountsDTO accountsDTO = new AccountsDTO();

        accountsDTO.setAccountId( source.getAccountId() );
        accountsDTO.setUserEmail( source.getUserEmail() );
        accountsDTO.setUserPassword( source.getUserPassword() );
        accountsDTO.setUserRole( source.getUserRole() );

        return accountsDTO;
    }

    @Override
    public AccountsDTO mergeToAccountsDTO(AccountsDTO accountsDTO, AccountCreateDTO accountCreateDTO) {
        if ( accountCreateDTO == null ) {
            return accountsDTO;
        }

        accountsDTO.setUserEmail( accountCreateDTO.getUserEmail() );
        accountsDTO.setUserPassword( accountCreateDTO.getUserPassword() );
        accountsDTO.setUserRole( accountCreateDTO.getUserRole() );

        return accountsDTO;
    }

    @Override
    public AccountDetailsDTO accountsEntityToAccountsDetailsDTO(AccountsEntity source) {
        if ( source == null ) {
            return null;
        }

        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();

        accountDetailsDTO.setUserEmail( source.getUserEmail() );
        accountDetailsDTO.setUserRole( source.getUserRole() );

        return accountDetailsDTO;
    }

    @Override
    public void updateAccountsDTO(AccountsDTO source, AccountsDTO target) {
        if ( source == null ) {
            return;
        }

        target.setAccountId( source.getAccountId() );
        target.setUserEmail( source.getUserEmail() );
        target.setUserPassword( source.getUserPassword() );
        target.setUserRole( source.getUserRole() );
    }

    @Override
    public void updateAccountsEntity(AccountsDTO source, AccountsEntity target) {
        if ( source == null ) {
            return;
        }

        target.setAccountId( source.getAccountId() );
        target.setUserEmail( source.getUserEmail() );
        target.setUserPassword( source.getUserPassword() );
        target.setUserRole( source.getUserRole() );
    }
}
