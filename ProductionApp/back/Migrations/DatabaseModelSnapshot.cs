﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using PyxisKapriBack.Models;

#nullable disable

namespace PyxisKapriBack.Migrations
{
    [DbContext(typeof(Database))]
    partial class DatabaseModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "6.0.10")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("PyxisKapriBack.Models.City", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int?>("CountryId")
                        .IsRequired()
                        .HasColumnType("int");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(50)
                        .HasColumnType("varchar(50)");

                    b.HasKey("Id");

                    b.HasIndex("CountryId");

                    b.ToTable("Cities");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Comment", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int?>("CommentParentId")
                        .HasColumnType("int");

                    b.Property<DateTime>("DateCreated")
                        .HasColumnType("datetime(6)");

                    b.Property<int>("PostId")
                        .HasColumnType("int");

                    b.Property<string>("Text")
                        .IsRequired()
                        .HasMaxLength(300)
                        .HasColumnType("varchar(300)");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("CommentParentId");

                    b.HasIndex("PostId");

                    b.HasIndex("UserId");

                    b.ToTable("Comments");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.CommentDislike", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int>("CommentId")
                        .HasColumnType("int");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("CommentId");

                    b.HasIndex("UserId");

                    b.ToTable("CommentDislikes");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.CommentLike", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int>("CommentId")
                        .HasColumnType("int");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("CommentId");

                    b.HasIndex("UserId");

                    b.ToTable("CommentLikes");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Country", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(50)
                        .HasColumnType("varchar(50)");

                    b.HasKey("Id");

                    b.ToTable("Countries");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Dislike", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int>("PostId")
                        .HasColumnType("int");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("PostId");

                    b.HasIndex("UserId");

                    b.ToTable("Dislikes");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Follow", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int>("FollowerId")
                        .HasColumnType("int");

                    b.Property<int>("FollowingId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("FollowerId");

                    b.HasIndex("FollowingId");

                    b.ToTable("Follow");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Image", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<string>("ImageName")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<int>("PostId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("PostId");

                    b.ToTable("Images");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Like", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int>("PostId")
                        .HasColumnType("int");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("PostId");

                    b.HasIndex("UserId");

                    b.ToTable("Likes");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Location", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<string>("Address")
                        .IsRequired()
                        .HasMaxLength(300)
                        .HasColumnType("varchar(300)");

                    b.Property<int>("CityID")
                        .HasColumnType("int");

                    b.Property<double>("Latitude")
                        .HasColumnType("double");

                    b.Property<double>("Longitude")
                        .HasColumnType("double");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(100)
                        .HasColumnType("varchar(100)");

                    b.HasKey("Id");

                    b.HasIndex("CityID");

                    b.ToTable("Locations");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Message", b =>
                {
                    b.Property<long>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("bigint");

                    b.Property<int>("ReceiverId")
                        .HasColumnType("int");

                    b.Property<int>("SenderId")
                        .HasColumnType("int");

                    b.Property<string>("Text")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<DateTime>("Time")
                        .HasColumnType("datetime(6)");

                    b.HasKey("Id");

                    b.HasIndex("ReceiverId");

                    b.HasIndex("SenderId");

                    b.ToTable("Messages");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Post", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<string>("CoverImageName")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<DateTime>("CreatedDate")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Description")
                        .IsRequired()
                        .HasMaxLength(300)
                        .HasColumnType("varchar(300)");

                    b.Property<string>("FullLocation")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<double?>("Latitude")
                        .HasColumnType("double");

                    b.Property<int>("LocationId")
                        .HasColumnType("int");

                    b.Property<double?>("Longitude")
                        .HasColumnType("double");

                    b.Property<string>("PostPath")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("LocationId");

                    b.HasIndex("UserId");

                    b.ToTable("Posts");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Role", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasMaxLength(50)
                        .HasColumnType("varchar(50)");

                    b.HasKey("Id");

                    b.ToTable("Roles");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int?>("CountryId")
                        .IsRequired()
                        .HasColumnType("int");

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<string>("FileName")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<string>("FirstName")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<string>("FolderPath")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<string>("LastName")
                        .IsRequired()
                        .HasColumnType("longtext");

                    b.Property<byte[]>("Password")
                        .IsRequired()
                        .HasColumnType("longblob");

                    b.Property<byte[]>("PasswordKey")
                        .IsRequired()
                        .HasColumnType("longblob");

                    b.Property<byte[]>("ProfileImage")
                        .HasColumnType("longblob");

                    b.Property<int?>("RoleId")
                        .IsRequired()
                        .HasColumnType("int");

                    b.Property<string>("Username")
                        .IsRequired()
                        .HasMaxLength(50)
                        .HasColumnType("varchar(50)");

                    b.HasKey("Id");

                    b.HasIndex("CountryId");

                    b.HasIndex("RoleId");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.View", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int");

                    b.Property<int>("PostId")
                        .HasColumnType("int");

                    b.Property<int>("UserId")
                        .HasColumnType("int");

                    b.HasKey("Id");

                    b.HasIndex("PostId");

                    b.HasIndex("UserId");

                    b.ToTable("Views");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.City", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Country", "Country")
                        .WithMany("Cities")
                        .HasForeignKey("CountryId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Country");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Comment", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Comment", "CommentParent")
                        .WithMany("Replies")
                        .HasForeignKey("CommentParentId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("PyxisKapriBack.Models.Post", "Post")
                        .WithMany("Comments")
                        .HasForeignKey("PostId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("Comments")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("CommentParent");

                    b.Navigation("Post");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.CommentDislike", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Comment", "Comment")
                        .WithMany("Dislikes")
                        .HasForeignKey("CommentId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("CommentDislikes")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Comment");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.CommentLike", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Comment", "Comment")
                        .WithMany("Likes")
                        .HasForeignKey("CommentId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("CommentLikes")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Comment");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Dislike", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Post", "Post")
                        .WithMany("Dislikes")
                        .HasForeignKey("PostId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("Dislikes")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Post");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Follow", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.User", "Follower")
                        .WithMany("Followers")
                        .HasForeignKey("FollowerId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "Following")
                        .WithMany("Following")
                        .HasForeignKey("FollowingId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Follower");

                    b.Navigation("Following");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Image", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Post", "Post")
                        .WithMany("Images")
                        .HasForeignKey("PostId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Post");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Like", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Post", "Post")
                        .WithMany("Likes")
                        .HasForeignKey("PostId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("Likes")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Post");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Location", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.City", "City")
                        .WithMany("Locations")
                        .HasForeignKey("CityID")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("City");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Message", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.User", "Receiver")
                        .WithMany("ReceivedMessages")
                        .HasForeignKey("ReceiverId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "Sender")
                        .WithMany("SentMessages")
                        .HasForeignKey("SenderId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Receiver");

                    b.Navigation("Sender");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Post", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Location", "Location")
                        .WithMany("Posts")
                        .HasForeignKey("LocationId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("Posts")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Location");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.User", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Country", "Country")
                        .WithMany("Users")
                        .HasForeignKey("CountryId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.Role", "Role")
                        .WithMany("Users")
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Country");

                    b.Navigation("Role");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.View", b =>
                {
                    b.HasOne("PyxisKapriBack.Models.Post", "Post")
                        .WithMany("Views")
                        .HasForeignKey("PostId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("PyxisKapriBack.Models.User", "User")
                        .WithMany("Views")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Post");

                    b.Navigation("User");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.City", b =>
                {
                    b.Navigation("Locations");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Comment", b =>
                {
                    b.Navigation("Dislikes");

                    b.Navigation("Likes");

                    b.Navigation("Replies");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Country", b =>
                {
                    b.Navigation("Cities");

                    b.Navigation("Users");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Location", b =>
                {
                    b.Navigation("Posts");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Post", b =>
                {
                    b.Navigation("Comments");

                    b.Navigation("Dislikes");

                    b.Navigation("Images");

                    b.Navigation("Likes");

                    b.Navigation("Views");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.Role", b =>
                {
                    b.Navigation("Users");
                });

            modelBuilder.Entity("PyxisKapriBack.Models.User", b =>
                {
                    b.Navigation("CommentDislikes");

                    b.Navigation("CommentLikes");

                    b.Navigation("Comments");

                    b.Navigation("Dislikes");

                    b.Navigation("Followers");

                    b.Navigation("Following");

                    b.Navigation("Likes");

                    b.Navigation("Posts");

                    b.Navigation("ReceivedMessages");

                    b.Navigation("SentMessages");

                    b.Navigation("Views");
                });
#pragma warning restore 612, 618
        }
    }
}
