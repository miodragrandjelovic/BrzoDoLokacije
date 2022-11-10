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

        public PostUI(IPostService postService, ILikeService likeService)
        {
            this.postService = postService;
            this.likeService = likeService;
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

        public List<PostDTO> GetAllPosts()
        {
            var posts = postService.GetAllPosts();

            var allPosts = new List<PostDTO>();

            foreach (var post in posts)
            {
                allPosts.Add(new PostDTO
                {
                    Id = post.Id,
                    CoverImage = Convert.ToBase64String(post.CoverImage),
                    NumberOfLikes = post.Likes != null ? post.Likes.Count() : 0,
                    NumberOfViews = 0,
                    Username = post.User.Username,
                    ProfileImage = Convert.ToBase64String(post.User.ProfileImage)
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
                images.Add(Convert.ToBase64String(image.ImageData));
            return new AdditionalPostData
            {
                Description = post.Description,
                Images = images
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
                    CoverImage = Convert.ToBase64String( post.CoverImage),
                    NumberOfLikes = likeService.GetNumberOfLikesByPostID(post.Id),
                    NumberOfViews = 0
                });
            }

            return postsDTO;
        }

        public Response SetLikeOnPost(int postID)
        {
            return postService.SetLikeOnPost(postID);
        }
    }
}
