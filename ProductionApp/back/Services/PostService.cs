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

        public void AddPost(PostDTO post)
        {

            var newPost = new Post();

            newPost.CreatedDate = DateTime.Now;
            newPost.User = userService.GetUser(userService.GetLoggedUser());
            newPost.Description = post.Description;
            newPost.LocationId = post.LocationId;   
            
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

        public void DeletePostById(int postID)
        {
            throw new NotImplementedException();
        }

        public void DeleteUserPost(int postID, int userName)
        {
            throw new NotImplementedException();
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


        public void SetLikeOnPost(int postID)
        {
            Post post = GetPost(postID);
            likeService.AddLike(post, userService.GetLoggedUser());

        }
    }
}
