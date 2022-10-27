using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class RoleDAL : IRoleDAL 
    {
        private Database _context; 
        public RoleDAL(Database context)
        {
            _context = context;
        }
        public Role GetUserRole()
        {
            return _context.Roles.Where(x => x.Name.Contains("user")).FirstOrDefault(); 
        }
    }
}
