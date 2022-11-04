﻿using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IUserService
    {
        void AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string usernameOrEmail);

        string? GetLoggedUser();
        
        string? GetUserEmail();

        string? GetRoleFromLoggedUser();
        Role GetUserRole();

        bool UpdateUserRole(string userName,string roleName);

        List<Role> GetAvailableRolesForUser(string user);

        bool UpdateUser(UserDTO user);

    }
}
