// 週次實體
@Entity
@Table(name = "weekly_grades")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyGradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String week;  // 例如: "W1", "W2" 等

    // 一個週次包含多個學生成績
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weeklyGrade", orphanRemoval = true)
    private List<StudentGradeEntity> studentGrades = new ArrayList<>();

    // 方便添加學生成績的輔助方法
    public void addStudentGrade(StudentGradeEntity grade) {
        studentGrades.add(grade);
        grade.setWeeklyGrade(this);
    }
}

// 學生成績實體
@Entity
@Table(name = "student_grades")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;  // 學生學號
    
    @ElementCollection
    @CollectionTable(
        name = "grade_details",
        joinColumns = @JoinColumn(name = "student_grade_id")
    )
    @Column(name = "grade")
    private List<Double> grades = new ArrayList<>();  // 該週的詳細成績列表

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekly_grade_id")
    private WeeklyGradeEntity weeklyGrade;
}

// 資料傳輸物件 (DTO)
@Getter
@Setter
@Builder
public class WeeklyGradeDTO {
    private String week;
    private List<StudentGradeDTO> studentGrades;
}

@Getter
@Setter
@Builder
public class StudentGradeDTO {
    private String studentId;
    private List<Double> grades;
}

// Repository
@Repository
public interface WeeklyGradeRepository extends JpaRepository<WeeklyGradeEntity, Long> {
    Optional<WeeklyGradeEntity> findByWeek(String week);
}

// Service
@Service
@Transactional
public class GradeService {
    @Autowired
    private WeeklyGradeRepository weeklyGradeRepository;

    // 添加某週的所有成績
    public void addWeeklyGrades(String week, List<StudentGradeDTO> studentGrades) {
        WeeklyGradeEntity weeklyGrade = WeeklyGradeEntity.builder()
                .week(week)
                .build();

        for (StudentGradeDTO gradeDTO : studentGrades) {
            StudentGradeEntity studentGrade = StudentGradeEntity.builder()
                    .studentId(gradeDTO.getStudentId())
                    .grades(gradeDTO.getGrades())
                    .build();
            weeklyGrade.addStudentGrade(studentGrade);
        }

        weeklyGradeRepository.save(weeklyGrade);
    }

    // 獲取某週的所有成績
    public WeeklyGradeDTO getWeeklyGrades(String week) {
        WeeklyGradeEntity weeklyGrade = weeklyGradeRepository.findByWeek(week)
                .orElseThrow(() -> new RuntimeException("Week not found: " + week));

        List<StudentGradeDTO> studentGradeDTOs = weeklyGrade.getStudentGrades().stream()
                .map(grade -> StudentGradeDTO.builder()
                        .studentId(grade.getStudentId())
                        .grades(grade.getGrades())
                        .build())
                .collect(Collectors.toList());

        return WeeklyGradeDTO.builder()
                .week(week)
                .studentGrades(studentGradeDTOs)
                .build();
    }
}
