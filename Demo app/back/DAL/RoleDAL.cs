using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.DTOComponents; 
namespace PyxisKapriBack.DAL
{
    public class RoleDAL : IRoleDAL 
    {
        private Database _context; 
        public RoleDAL(Database context)
        {
            _context = context;
        }
        public string GetUserRole()
        {
            var role = _context.Roles.Where(x => x.Name.Contains("user")).FirstOrDefault();

            return role.Name; 
        }
    }
}
