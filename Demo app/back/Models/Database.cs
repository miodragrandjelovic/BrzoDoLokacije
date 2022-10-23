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
            modelBuilder.Entity<City>()
            .HasOne(city => city.Country)
            .WithMany(country => country.Cities)
            .HasForeignKey(city => city.CountryId)
            .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<User>()
            .HasOne(user => user.Country)
            .WithMany(country => country.Users)
            .HasForeignKey(user => user.CountryId)
            .OnDelete(DeleteBehavior.Cascade);

        }

        public DbSet<User> Users { get; set; }
        public DbSet<Country> Countries{ get; set; }
        public DbSet<City> Cities { get; set; }
    }
}
