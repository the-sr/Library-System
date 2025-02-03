package library.services.impl;

import library.dto.AuthorDto;
import library.dto.BookDto;
import library.dto.GenreDto;
import library.dto.res.PagewiseRes;
import library.exception.CustomException;
import library.models.Book;
import library.models.BookAuthor;
import library.models.BookGenre;
import library.repository.BookAuthorRepo;
import library.repository.BookGenreRepo;
import library.repository.BookRepo;
import library.services.AuthorService;
import library.services.BookService;
import library.services.GenreService;
import library.services.mappers.AuthorMapper;
import library.services.mappers.BookMapper;
import library.services.mappers.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final BookAuthorRepo bookAuthorRepo;
    private final GenreService genreService;
    private final BookGenreRepo bookGenreRepo;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    @Override
    public String add(BookDto req) {
        Book book = bookRepo.save(bookMapper.dtoToEntity(req));
        if (req.getAuthors() != null && !req.getAuthors().isEmpty()) {
            req.getAuthors().parallelStream().forEach(author -> {
                author = authorService.add(author);
                BookAuthor bookAuthor = BookAuthor.builder()
                        .bookId(book.getId())
                        .authorId(author.getId())
                        .build();
                bookAuthorRepo.save(bookAuthor);
            });
        }
        if (req.getGenre() != null && !req.getGenre().isEmpty()) {
            req.getGenre().parallelStream().forEach(genre -> {
                genre = genreService.add(genre);
                BookGenre bookGenre = BookGenre.builder()
                        .bookId(book.getId())
                        .genreId(genre.getId())
                        .build();
                bookGenreRepo.save(bookGenre);
            });
        }
        return "Book added successfully";
    }

    @Override
    public BookDto findById(Long id) {
        Book book=bookRepo.findById(id).orElseThrow(()->new CustomException("Book not found", HttpStatus.NOT_FOUND));
        BookDto res=bookMapper.entityToDto(book);
        res.setAuthors(getAuthorDtoList(res));
        res.setGenre(getGenreDtoList(res));
        return res;
    }

    @Override
    public List<BookDto> findByTitle(String title) {
        List<Book> bookList=bookRepo.findByTitle(title);
        List<BookDto> res=new ArrayList<>();

        if(bookList!=null && !bookList.isEmpty()){
            bookList.parallelStream().forEach(book -> {
                BookDto bookDto=bookMapper.entityToDto(book);
                bookDto.setAuthors(getAuthorDtoList(bookDto));
                bookDto.setGenre(getGenreDtoList(bookDto));
                res.add(bookDto);
            });
        }
        return res;
    }

    @Override
    public Set<BookDto> findByAuthor(String authorName) {
        List<AuthorDto> authorDtoList=authorService.getByAuthorName(authorName);
        Set<BookDto> res=new HashSet<>();
        if(authorDtoList!=null && !authorDtoList.isEmpty()){
            authorDtoList.parallelStream().forEach(author -> {
                List<BookAuthor> bookAuthorList=bookAuthorRepo.findAllByAuthorId(author.getId());
                bookAuthorList.parallelStream().forEach(bookAuthor -> {
                    BookDto bookDto=bookMapper.entityToDto(bookAuthor.getBook());
                    bookDto.setAuthors(getAuthorDtoList(bookDto));
                    bookDto.setGenre(getGenreDtoList(bookDto));
                    res.add(bookDto);
                });
            });
        }
        return res;
    }

    @Override
    public Set<BookDto> findByGenre(String genreName) {
        List<GenreDto> genreDtoList=genreService.getByGenreName(genreName);
        Set<BookDto> res=new HashSet<>();
        if(genreDtoList!=null && !genreDtoList.isEmpty()){
            genreDtoList.parallelStream().forEach(genre -> {
                List<BookGenre> bookGenreList=bookGenreRepo.findAllByGenreId(genre.getId());
                bookGenreList.parallelStream().forEach(bookGenre -> {
                    BookDto bookDto=bookMapper.entityToDto(bookGenre.getBook());
                    bookDto.setGenre(getGenreDtoList(bookDto));
                    bookDto.setAuthors(getAuthorDtoList(bookDto));
                    res.add(bookDto);
                });
            });
        }
        return res;
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> bookList=bookRepo.findAll();
        List<BookDto>res=new ArrayList<>();
        if(!bookList.isEmpty()){
            bookList.parallelStream().forEach(book->{
                BookDto bookDto=bookMapper.entityToDto(book);
                bookDto.setAuthors(getAuthorDtoList(bookDto));
                bookDto.setGenre(getGenreDtoList(bookDto));
                res.add(bookDto);
            });
        }
        return res;
    }

    @Override
    public PagewiseRes<BookDto> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> books=bookRepo.findAll(pageable);
        List<BookDto> bookDtoList=new ArrayList<>();
        books.stream().forEach(book -> {
            BookDto bookDto=bookMapper.entityToDto(book);
            bookDto.setAuthors(getAuthorDtoList(bookDto));
            bookDto.setGenre(getGenreDtoList(bookDto));
            bookDtoList.add(bookDto);
        });
        PagewiseRes<BookDto> pagewiseRes = new PagewiseRes<>();
        pagewiseRes.setRes(bookDtoList);
        pagewiseRes.setTotalPages(books.getTotalPages());
        pagewiseRes.setTotalElements(books.getTotalElements());
        pagewiseRes.setCurrentPage(books.getNumber());
        pagewiseRes.setPageSize(books.getSize());
        pagewiseRes.setLast(books.isLast());
        return pagewiseRes;
    }

    @Transactional
    @Override
    public String removeById(Long id) {
        bookAuthorRepo.deleteAllByBookId(id);
        bookGenreRepo.deleteAllByBookId(id);
        bookRepo.deleteById(id);
        return "Book deleted successfully";
    }

    @Override
    public BookDto updateById(BookDto req) {
        return null;
    }

    private List<AuthorDto> getAuthorDtoList(BookDto bookDto) {
        List<BookAuthor> bookAuthorList=bookAuthorRepo.findAllByBookId(bookDto.getId());
        List<AuthorDto> authorDtoList=new ArrayList<>();
        bookAuthorList.parallelStream().forEach(bookAuthor -> {
            authorDtoList.add(authorMapper.entityToDto(bookAuthor.getAuthor()));
        });
        return authorDtoList;
    }

    private List<GenreDto> getGenreDtoList(BookDto bookDto) {
        List<BookGenre> bookGenreList=bookGenreRepo.findAllByBookId(bookDto.getId());
        List<GenreDto> genreDtoList=new ArrayList<>();
        bookGenreList.parallelStream().forEach(bookGenre -> {
            genreDtoList.add(genreMapper.entityToDto(bookGenre.getGenre()));
        });
        return genreDtoList;
    }
}
