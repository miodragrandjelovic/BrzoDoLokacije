using PyxisKapriBack.Constants; 
namespace PyxisKapriBack.Models
{
    public class Database : DbContext
    {
        public Database(DbContextOptions<Database> options) : base(options) {
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
                optionsBuilder.UseMySql(ServerConstants.CONN_STRING, ServerVersion.AutoDetect(ServerConstants.CONN_STRING));
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            #region 'City Relationships'

            modelBuilder.Entity<City>()
            .HasOne(city => city.Country)
            .WithMany(country => country.Cities)
            .HasForeignKey(city => city.CountryId)
            .OnDelete(DeleteBehavior.Cascade);

            #endregion

            #region 'User Relationships'

            modelBuilder.Entity<User>()
            .HasOne(user => user.Country)
            .WithMany(country => country.Users)
            .HasForeignKey(user => user.CountryId)
            .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<User>()
            .HasOne(user => user.Role)
            .WithMany(role => role.Users)
            .HasForeignKey(user => user.RoleId)
            .OnDelete(DeleteBehavior.Cascade);
            #endregion

            #region 'Post Relationships'

            modelBuilder.Entity<Post>()
           .HasOne(post => post.User)
           .WithMany(user => user.Posts)
           .HasForeignKey(post => post.UserId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Post>()
            .HasOne(post => post.Location)
            .WithMany(location => location.Posts)
            .HasForeignKey(post => post.LocationId)
            .OnDelete(DeleteBehavior.Cascade);

            #endregion
        }

        #region 'Database Datasets'
        public DbSet<User> Users { get; set; }
        public DbSet<Country> Countries{ get; set; }
        public DbSet<City> Cities { get; set; }

        public DbSet<Post> Posts { get; set; }
        public DbSet<Location> Locations { get; set; }

        public DbSet<Role> Roles { get; set; }

        #endregion

        public void InsertDefaultValues()
        {
            Role role = Roles.Where(x => x.Name.Contains("admin")).FirstOrDefault();
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = "admin"
                });
                SaveChanges();
            }

            role = Roles.Where(x => x.Name.Contains("user")).FirstOrDefault();
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = "user"
                });
                SaveChanges();
            }

            role = Roles.Where(x => x.Name.Contains("moderator")).FirstOrDefault();
            if (role == null)
            {
                Roles.Add(new Role
                {
                    Name = "moderator"
                });
                SaveChanges();
            }
        }
    }
}
