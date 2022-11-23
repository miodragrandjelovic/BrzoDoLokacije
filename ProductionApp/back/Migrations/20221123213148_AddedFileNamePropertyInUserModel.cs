using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PyxisKapriBack.Migrations
{
    public partial class AddedFileNamePropertyInUserModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "FileName",
                table: "Users",
                type: "longtext",
                nullable: false)
                .Annotation("MySql:CharSet", "utf8mb4");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "FileName",
                table: "Users");
        }
    }
}
