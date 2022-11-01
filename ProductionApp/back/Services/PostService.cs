using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.DTOComponents;
using System.Text;

namespace PyxisKapriBack.Services
{
    public class PostService : IPostService
    {
        private readonly IPostDAL postDAL;
        private readonly ILikeService likeService;
        private readonly IUserService userService;

        public PostService(IPostDAL postDAL, ILikeService likeService, IUserService userService)
        {
            this.postDAL = postDAL;
            this.likeService = likeService;
            this.userService = userService;
        }

        public void AddPost(NewPostDTO post)
        {

            var newPost = new Post();

            newPost.CreatedDate = DateTime.Now;
            newPost.User = userService.GetUser(userService.GetLoggedUser());
            newPost.Description = post.Description;
            newPost.LocationId = post.LocationId;   
            newPost.CoverImage = post.CoverImage;
            foreach(string image in post.Images)
            {
                Image newImage = new Image();
                newImage.ImageData = Encoding.ASCII.GetBytes(image);
                newPost.Images.Add(newImage);
            }


            postDAL.AddPost(newPost);
        }

        public void DeletePost(int postID)
        {
            postDAL.DeletePost(postID);
        }

        public bool DeleteUserPost(int postID, string userName)
        {
            var user = userService.GetUser(userName);
            if(user == null)
                return false;
            
            var post = postDAL.GetPost(postID);
            if (post == null)
                return false;

            if (!user.Id.Equals(post.UserId))
                return false;

            postDAL.DeletePost(postID);

            return true;

        }

        public Post GetPost(int PostID)
        {
            return postDAL.GetPost(PostID);
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }

        public List<PostDTO> GetUserPosts(string username)
        {
            var posts = postDAL.GetUserPosts(username);
            var postsDTO = new List<PostDTO>();

            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                    CoverImage = post.CoverImage,
                    NumberOfLikes = likeService.GetNumberOfLikesByPostID(post.Id),
                    NumberOfViews = 0
                });
            }

            return postsDTO;
        }


        public void SetLikeOnPost(int postID)
        {
            Post post = GetPost(postID);
            likeService.AddLike(post, userService.GetLoggedUser());

        }
    }
}
