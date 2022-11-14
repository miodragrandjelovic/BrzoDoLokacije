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
        private readonly ILocationDAL locationDAL;

        public PostService(IPostDAL postDAL, ILikeService likeService, IUserService userService, ILocationDAL locationDAL)
        {
            this.postDAL = postDAL;
            this.likeService = likeService;
            this.userService = userService;
            this.locationDAL = locationDAL;
        }

        public void AddPost(NewPostDTO post)
        {

            var newPost = new Post();
            newPost.User = userService.GetUser(userService.GetLoggedUser());
            newPost.CreatedDate = DateTime.Now;
            newPost.Description = post.Description;
            newPost.CoverImage = Convert.FromBase64String(post.CoverImage);


            var location = locationDAL.GetLocation(post.LocationName);
            if (location == null)
                locationDAL.AddLocation(post.LocationName, post.City, post.Country);

            newPost.Location = locationDAL.GetLocation(post.LocationName);
            newPost.Location.Latitude = post.Latitude;
            newPost.Location.Longitude = post.Lognitude;
            newPost.Location.Address = post.Address;



            foreach (string image in post.Images)
            {
                Image newImage = new Image();
                newImage.ImageData = Encoding.ASCII.GetBytes(image);
                newPost.Images.Add(newImage);
            }


            postDAL.AddPost(newPost);
        }
        public Response DeletePost(int postID)
        {
            bool succeed = postDAL.DeletePost(postID);
            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status500InternalServerError,
                    Message = "Post doesn't exist!"
                };

            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "Post deleted succesffuly!"
            };
        }
        public Response DeleteUserPost(int postID)
        {
            var response = new Response();
            response.StatusCode = StatusCodes.Status404NotFound;

            var user = userService.GetUser(userService.GetLoggedUser());
            var post = GetPost(postID);

            
            if (user == null)
            {
                response.Message = "User not found!";
                return response;
            }

            if (post == null)
            {
                response.Message = "Post not found";
                return response;
            }
            if(!user.Id.Equals(post.UserId))
            {
                response.StatusCode=StatusCodes.Status403Forbidden;
                response.Message = "Permission denied!";
                return response;
            }

            postDAL.DeletePost(postID);

            response.StatusCode = StatusCodes.Status200OK;
            response.Message = "Post deleted succesffuly!";
            return response;

        }
        public List<Post> GetAllPosts()
        {
            return postDAL.GetPosts(userService.GetLoggedUser());
        }
        public Post GetPost(int PostID)
        {
            return postDAL.GetPost(PostID);
        }
        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }
        public List<Post> GetUserPosts(string username)
        {
            return postDAL.GetUserPosts(username);
        }

        public Response SetLikeOnPost(int postID)
        {
            return likeService.AddLike(postID);
        }
        public List<Post> GetFollowingPosts(string username)
        {
            return postDAL.GetFollowingPosts(username); 
        }
        public List<Post> GetRecommendedPosts(string username)
        {
            return postDAL.GetRecommendedPosts(username); 
        }
    }
}
