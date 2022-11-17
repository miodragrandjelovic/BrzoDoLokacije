using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface ICommentUI
    {
        public Response AddComment(NewCommentDTO comment);
        public Response DeleteComment(int commentId);

        public CommentDTO GetComment(int commentId);
        public List<CommentDTO> GetCommentsPost(int postId);
    }
}
