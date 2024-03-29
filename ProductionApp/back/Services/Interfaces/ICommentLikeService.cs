﻿using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface ICommentLikeService
    {
        public bool IsCommentLiked(int commentID);
        public Response ChangeLikeStateOnComment(Comment comment);
        public Response DeleteLikeFromComment(int commentID);
        public List<CommentLike> GetLikesOfComment(int commentID);
        public List<User> GetUsersWhoLiked(int commentID);
        public int GetCommentLikeCount(int commentID);
    }
}
