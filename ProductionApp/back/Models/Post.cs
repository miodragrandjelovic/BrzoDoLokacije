namespace PyxisKapriBack.Models
{
    public class Post
    {
        [Key]
        public int Id { get; set; }
        [MaxLength(Constants.ModelConstants.LENGTH_DESCRIPTION)]
        public string Description { get; set; }

        public DateTime CreatedDate { get; set; }

        public List<Like> Likes { get; set; } = new List<Like>();
        public List<Dislike> Dislikes { get; set; } = new List<Dislike>();
        public string CoverImage { get; set; } = string.Empty;
        public List<Image> Images { get; set; } = new List<Image>();
        #region 'User'
        public int UserId { get; set; }
        public User User { get; set; }

        #endregion

        #region 'Location'
        public int LocationId { get; set; }
        public Location Location { get; set; }

        #endregion
    }
}
