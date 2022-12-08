﻿using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IUserService
    {
        Response AddNewUser(User user);
        Task<bool> UserAlreadyExists(string username);

        User? GetUser(string usernameOrEmail);

        string? GetLoggedUser();
        
        string? GetUserEmail();

        string? GetRoleFromLoggedUser();
        Role GetUserRole();

        Response UpdateUserRole(string userName,string roleName);

        List<Role> GetAvailableRolesForUser(string user);

        Response UpdateUser(UserDTO user);

        List<User> GetAllUsers();
        Response ChangeUserPassword(CredentialsDTO credentials);


        double GetAverageGradeForAllPosts(string username);
        int GetDifferentLocations(string username); 

        List<Connection> GetUserConnections(string username);
        Connection GetConnectionById(string connectionId);

        bool AddNewConnection(Connection connection);

    }
}
