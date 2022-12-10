﻿namespace PyxisKapriBack.Models
{
    public class Post
    {
        [Key]
        public int Id { get; set; }
        [MaxLength(Constants.ModelConstants.LENGTH_POST)]
        public string Description { get; set; }

        public DateTime CreatedDate { get; set; }

        public List<Like>? Likes { get; set; }
        public List<Dislike>? Dislikes { get; set; }
        public List<Comment>? Comments { get; set; }
        public string CoverImageName { get; set; } = string.Empty;
        public string PostPath { get; set; } = string.Empty;
        public List<Image> Images { get; set; } = new List<Image>();

        public List<View> Views { get; set; } = new List<View>();
        public double? Latitude { get; set; }
        public double? Longitude { get; set; }

        public String FullLocation { get; set; } = Constants.Constants.UNKNWOWN;
        public String? Tags { get; set; } = String.Empty;
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
