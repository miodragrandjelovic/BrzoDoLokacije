using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL
{
    public class RoleDAL : IRoleDAL 
    {
        private Database _context; 
        public RoleDAL(Database context)
        {
            _context = context;
        }

        public Role GetAdministratorRole()
        {
            var role = _context.Roles.Where(x => x.Name.Contains(Constants.Constants.ADMIN)).FirstOrDefault();

            return role;
        }

        public Role GetModeratorRole()
        {
            var role = _context.Roles.Where(x => x.Name.Contains(Constants.Constants.MODERATOR)).FirstOrDefault();

            return role;
        }

        public Role GetUserRole()
        {
            var role = _context.Roles.Where(x => x.Name.Equals(Constants.Constants.USER)).FirstOrDefault();

            return role; 
        }
        public Role GetRole(string roleName)
        {
            var role = _context.Roles.Where(x => x.Name.Equals(roleName)).FirstOrDefault();

            return role;
        }
    }
}
