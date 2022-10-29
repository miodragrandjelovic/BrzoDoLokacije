namespace PyxisKapriBack.Models
{
    public partial class Database
    {
        private void InsertRoles()
        {
            Role role = Roles.Where(x => x.Name.Contains(Constants.Constants.UNKNWOWN)).FirstOrDefault();
            
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = Constants.Constants.UNKNWOWN
                });
                SaveChanges();
            }

            role = Roles.Where(x => x.Name.Contains(Constants.Constants.ADMIN)).FirstOrDefault();
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = Constants.Constants.ADMIN
                });
                SaveChanges();
            }

            role = Roles.Where(x => x.Name.Contains(Constants.Constants.USER)).FirstOrDefault();
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = Constants.Constants.USER
                });
                SaveChanges();
            }

            role = Roles.Where(x => x.Name.Contains(Constants.Constants.MODERATOR)).FirstOrDefault();
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = Constants.Constants.MODERATOR
                });
                SaveChanges();
            }
        }
    }
}
