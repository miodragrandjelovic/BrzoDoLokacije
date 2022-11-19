using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services
{
    public class CommentLikeService : ICommentLikeService
    {
        private readonly ICommentLikeDAL _iCommentLikeDAL;
        
        public CommentLikeService(ICommentLikeDAL commentLikeDAL)
        {
            _iCommentLikeDAL = commentLikeDAL;
        }
    }
}
