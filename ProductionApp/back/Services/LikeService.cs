﻿using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services
{
    public class LikeService : ILikeService
    {
        private readonly ILikeDAL likeDAL;
        private readonly IUserService userService;

        public LikeService(ILikeDAL likeDAL,IUserService userService)
        {
            this.likeDAL = likeDAL;
            this.userService = userService;
        }

        public Response AddLike(int postID, string username)
        {
            likeDAL.AddLike(postID, userService.GetLoggedUser());
            var succeed = likeDAL.AddLike(postID, userService.GetLoggedUser());
            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status500InternalServerError,
                    Message = "Error!"
                };

            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "Success!"
            };
        }

        public void DeleteLike(int likeID)
        {
            likeDAL.DeleteLike(likeID);
        }

        public List<Like> GetLikes(int postID, out int numberOfLikes)
        {
            return likeDAL.GetLikes(postID, out numberOfLikes);
        }

        public int GetNumberOfLikesByPostID(int postID)
        {
            int numberOfLikes = 0;

            likeDAL.GetLikes(postID, out numberOfLikes);


            return numberOfLikes;
        }
    }
}
