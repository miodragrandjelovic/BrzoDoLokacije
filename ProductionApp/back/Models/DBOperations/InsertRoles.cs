namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        public Role InsertRole(string RoleName)
        {
            Role role = GetRole(RoleName);

            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = RoleName
                });
                SaveChanges();
            }
            return Roles.Where(x => x.Name.Equals(RoleName)).FirstOrDefault();
        }
        public Role GetRole(string name)
        {
            return Roles.Where(x => x.Name.Equals(name)).FirstOrDefault();
        }
        private void InsertRoles()
        {
            InsertRole(Constants.Constants.UNKNWOWN);
            InsertRole(Constants.Constants.ADMIN);
            InsertRole(Constants.Constants.USER);
            InsertRole(Constants.Constants.MODERATOR);
        }
    }
}
