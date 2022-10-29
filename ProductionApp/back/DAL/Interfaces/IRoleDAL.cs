using PyxisKapriBack.DTOComponents;

namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IRoleDAL
    {
        public Role GetUserRole();
        public Role GetAdministratorRole();
        public Role GetModeratorRole(); 
    }
}
