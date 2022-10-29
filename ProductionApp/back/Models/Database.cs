using PyxisKapriBack.Constants; 
namespace PyxisKapriBack.Models
{
    public partial class Database : DbContext
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

            #region 'Like Relationships'

            modelBuilder.Entity<Like>()
           .HasOne(like => like.Post)
           .WithMany(post => post.Likes)
           .HasForeignKey(like => like.PostId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Like>()
            .HasOne(like => like.User)
            .WithMany(user => user.Likes)
            .HasForeignKey(like => like.UserId)
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

        public DbSet<Like> Likes { get; set; }
        #endregion

        #region 'Insert Data'
        public void InsertDefaultValues()
        {
            InsertRoles();
            InsertCountries();
        }
        #endregion
    }
}
