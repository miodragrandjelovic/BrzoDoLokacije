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

            #region 'Image Relationships'

            modelBuilder.Entity<Image>()
            .HasOne(image => image.Post)
            .WithMany(post => post.Images)
            .HasForeignKey(image => image.PostId)
            .OnDelete(DeleteBehavior.Cascade);

            #endregion

            #region 'Dislike Relationships'

            modelBuilder.Entity<Dislike>()
           .HasOne(dislike => dislike.Post)
           .WithMany(post => post.Dislikes)
           .HasForeignKey(dislikes => dislikes.PostId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Dislike>()
            .HasOne(dislike => dislike.User)
            .WithMany(user => user.Dislikes)
            .HasForeignKey(dislike => dislike.UserId)
            .OnDelete(DeleteBehavior.Cascade);
            #endregion

            #region 'Comment Relationships'

            modelBuilder.Entity<Comment>()
           .HasOne(comment => comment.Post)
           .WithMany(post => post.Comments)
           .HasForeignKey(comment => comment.PostId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Comment>()
            .HasOne(comment => comment.User)
            .WithMany(user => user.Comments)
            .HasForeignKey(comment => comment.UserId)
            .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Comment>()
            .HasOne(comment => comment.CommentParent)
            .WithMany(parent => parent.Replies)
            .HasForeignKey(comment => comment.CommentParentId)
            .OnDelete(DeleteBehavior.Cascade);

            #endregion

            #region 'Follow Relationships'

            modelBuilder.Entity<Follow>()
           .HasOne(follow => follow.Follower)
           .WithMany(follower => follower.Followers)
           .HasForeignKey(follow => follow.FollowerId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Follow>()
            .HasOne(follow => follow.Following)
            .WithMany(following => following.Following)
            .HasForeignKey(follow => follow.FollowingId)
            .OnDelete(DeleteBehavior.Cascade);

            #endregion

            #region 'Comment Like Relationships'

            modelBuilder.Entity<CommentLike>()
           .HasOne(commentLike => commentLike.Comment)
           .WithMany(comment => comment.Likes)
           .HasForeignKey(commentLike => commentLike.CommentId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<CommentLike>()
            .HasOne(commentLike => commentLike.User)
            .WithMany(user => user.CommentLikes)
            .HasForeignKey(commentLike => commentLike.UserId)
            .OnDelete(DeleteBehavior.Cascade);
            #endregion

            #region 'Comment Dislike Relationships'

            modelBuilder.Entity<CommentDislike>()
           .HasOne(commentDislike => commentDislike.Comment)
           .WithMany(comment => comment.Dislikes)
           .HasForeignKey(commentDislike => commentDislike.CommentId)
           .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<CommentDislike>()
            .HasOne(commentDislike => commentDislike.User)
            .WithMany(user => user.CommentDislikes)
            .HasForeignKey(commentDislike => commentDislike.UserId)
            .OnDelete(DeleteBehavior.Cascade);
            #endregion

            #region 'Message Relationships'

            modelBuilder.Entity<Message>()
            .HasOne(message => message.Sender)
            .WithMany(sender => sender.SentMessages)
            .HasForeignKey(message => message.SenderId)
            .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Message>()
            .HasOne(message => message.Receiver)
            .WithMany(receiver => receiver.ReceivedMessages)
            .HasForeignKey(message => message.ReceiverId)
            .OnDelete(DeleteBehavior.Cascade);

            #endregion

            #region 'Connection Relationships'

            modelBuilder.Entity<Connection>()
                .HasOne(connection => connection.User)
                .WithMany(user => user.Connections)
                .HasForeignKey(connection => connection.UserId)
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
        public DbSet<Dislike> Dislikes { get; set; }
        public DbSet<Image> Images { get; set; }
        public DbSet<Comment> Comments { get; set; }
        public DbSet<Follow> Follow { get; set; }
        public DbSet<CommentLike> CommentLikes { get; set; }
        public DbSet<CommentDislike> CommentDislikes { get; set; }
        public DbSet<Message> Messages { get; set; }
        public DbSet<View> Views { get; set; }

        public DbSet<Connection> Connections { get; set; }
        #endregion

        #region 'Insert Data'
        public void InsertDefaultValues()
        {
            InsertRoles();
            InsertCountries();
            InsertCities();
            InsertLocations(); 
        }
        #endregion
    }
}
