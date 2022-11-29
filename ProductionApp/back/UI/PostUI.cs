using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class PostUI : IPostUI
    {
        private readonly IPostService postService;
        private readonly ILikeService likeService;
        private readonly IUserService userService;

        public PostUI(IPostService postService, ILikeService likeService, IUserService userService)
        {
            this.postService = postService;
            this.likeService = likeService;
            this.userService = userService;
        }
        public void AddPost(NewPostDTO post)
        {
            postService.AddPost(post); 
        }

        public Response DeletePost(int postID)
        {
            return postService.DeletePost(postID);
        }

        public Response DeleteUserPost(int postID)
        {
            return postService.DeleteUserPost(postID);
        }

        public List<PostDTO> GetAllPosts(SortType sortType = SortType.DATE)
        {
            var posts = postService.GetAllPosts(sortType);

            var allPosts = new List<PostDTO>();

            foreach (var post in posts)
            {
                allPosts.Add(new PostDTO
                {
                    Id = post.Id,
                    FullProfileImagePath = Path.Combine(post.User.FolderPath,post.User.FileName),
                    NumberOfLikes = post.Likes != null ? post.Likes.Count() : 0,
                    NumberOfViews = 0,
                    Username = post.User.Username,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath,post.PostPath,post.CoverImageName),
                    IsLiked = likeService.IsLiked(post.Id, userService.GetLoggedUser()),
                    DateCreated = post.CreatedDate.ToString("g")

                });
            }
            return allPosts;
        }

        public List<PostDTO> GetFollowingPosts(SortType sortType = SortType.DATE)
        {
            var posts = postService.GetFollowingPosts(userService.GetLoggedUser(), sortType);

            var allPosts = new List<PostDTO>();

            foreach (var post in posts)
            {
                allPosts.Add(new PostDTO
                {
                    Id = post.Id,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    NumberOfLikes = post.Likes != null ? post.Likes.Count() : 0,
                    NumberOfViews = 0,
                    Username = post.User.Username,
                    FullProfileImagePath = Path.Combine(post.User.FolderPath, post.User.FileName),
                    IsLiked = likeService.IsLiked(post.Id, userService.GetLoggedUser()),
                    DateCreated = post.CreatedDate.ToString("g")
                });
            }
            return allPosts;
        }
        public List<PostDTO> GetRecommendedPosts(SortType sortType = SortType.DATE)
        {
            var posts = postService.GetRecommendedPosts(userService.GetLoggedUser(), sortType);

            var allPosts = new List<PostDTO>();

            foreach (var post in posts)
            {
                allPosts.Add(new PostDTO
                {
                    Id = post.Id,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    NumberOfLikes = post.Likes != null ? post.Likes.Count() : 0,
                    NumberOfViews = 0,
                    Username = post.User.Username,
                    FullProfileImagePath = Path.Combine(post.User.FolderPath, post.User.FileName),
                    IsLiked = likeService.IsLiked(post.Id, userService.GetLoggedUser()),
                    DateCreated = post.CreatedDate.ToString("g")
                });
            }
            return allPosts;
        }
        public AdditionalPostData GetPost(int PostID)
        {
            var post = postService.GetPost(PostID);
            if(post == null)
                return null;
            var images = new List<string>();

            foreach (var image in post.Images)
            {
                var fullPath = Path.Combine(post.User.FolderPath, post.PostPath, image.ImageName);
                images.Add(fullPath); 
            }
            return new AdditionalPostData
            {
                Description = post.Description,
                Latitude = post.Location.Latitude,
                Longitude = post.Location.Longitude,
                Address = post.Location.Address,
                City = string.Empty,
                Country = string.Empty,
                Images = images,
                NumberOfComments = post.Comments != null ? post.Comments.Count() : 0,
            };

           
        }

        public List<PostDTO> GetPostsForLocation(int LocationID)
        {
            var posts = postService.GetPostsForLocation(LocationID);
            var postsDTO = new List<PostDTO>();

            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                   // CoverImage = post.CoverImage,
                    NumberOfLikes = likeService.GetNumberOfLikesByPostID(post.Id),
                    NumberOfViews = 0
                });
            }

            return postsDTO;
        }

        public List<PostDTO> GetUserPosts(string username)
        {
            var posts = postService.GetUserPosts(username);
            var postsDTO = new List<PostDTO>();

            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                    Id = post.Id,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    NumberOfLikes = likeService.GetNumberOfLikesByPostID(post.Id),
                    NumberOfViews = 0,
                    Username = post.User.Username.ToString(),
                    FullProfileImagePath = Path.Combine(post.User.FolderPath, post.User.FileName),
                    DateCreated = post.CreatedDate.ToString("g")
                }) ;
            }

            return postsDTO;
        }

        public Response RemoveLikeFromPost(int postID)
        {
            return likeService.DeleteLike(postID);
        }

        public Response SetLikeOnPost(int postID)
        {
            return postService.SetLikeOnPost(postID);
        }
    }
}
