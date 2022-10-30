using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IRoleDAL
    {
        public Role GetUserRole();
        public Role GetAdministratorRole();
        public Role GetModeratorRole();
        public Role GetRole(string roleName);

        List<Role> GetAvailableRolesForUser(User user);
    }
}
