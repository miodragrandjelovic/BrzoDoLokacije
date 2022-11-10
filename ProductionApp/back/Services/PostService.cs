﻿using PyxisKapriBack.DAL.Interfaces;
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
            newPost.CoverImage = Convert.FromBase64String(post.CoverImage);
            foreach(string image in post.Images)
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
                    StatusCode = StatusCodes.Status404NotFound,
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
            var loggedUser = userService.GetLoggedUser();
            var role = userService.GetRoleFromLoggedUser();
            var post = postDAL.GetPost(PostID);

            if (post.User.Username.Equals(loggedUser) || role.Equals(Constants.Constants.ADMIN))
                return post;

            return null;
            
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
