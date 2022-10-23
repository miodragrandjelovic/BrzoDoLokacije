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
        }

        public DbSet<User> Users { get; set; }
    }
}
