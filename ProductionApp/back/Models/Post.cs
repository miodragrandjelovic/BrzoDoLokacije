namespace PyxisKapriBack.Models
{
    public class Post
    {
        [Key]
        public int Id { get; set; }
        [MaxLength(Constants.ModelConstants.LENGTH_DESCRIPTION)]
        public string Description { get; set; }

        public DateTime CreatedDate { get; set; }

        public string ImagePath { get; set; }

        public List<Like> Likes { get; set; }
        public List<Image> Images { get; set; }
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
