import {
  FormControl,
  FormErrorMessage,
  FormLabel,
} from "@chakra-ui/form-control";
import { Input } from "@chakra-ui/input";
import { Textarea } from "@chakra-ui/textarea";
import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";
import DropdownWithInputText from "../../../building-blocks/DropdownWithInputText";

const ProjectFormSchema = z.object({
  name: z.string().min(2).max(255),
  description: z.string().max(1000),
  category: z.string().min(2).max(255),
  tags: z.array(z.string().min(2).max(255)),
});

const defaultValues = {
  name: "",
  description: "",
  category: "",
  tags: [],
};

type ProjectFormValues = z.infer<typeof ProjectFormSchema>;

const CreateProjectForm = () => {
  const {
    control,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<ProjectFormValues>({
    resolver: zodResolver(ProjectFormSchema),
    defaultValues: defaultValues,
  });

  const onFormSubmit = (data: ProjectFormValues) => {
    console.log(data);
  };

  return (
    <form onSubmit={handleSubmit(onFormSubmit)}>
      <FormControl isInvalid={!!errors.name}>
        <FormLabel>Name</FormLabel>
        <Controller
          name="name"
          control={control}
          render={({ field }) => (
            <Input {...field} type="text" placeholder="Name of the project" />
          )}
        />
        <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
      </FormControl>

      <FormControl isInvalid={!!errors.description}>
        <FormLabel>Description</FormLabel>
        <Controller
          name="description"
          control={control}
          render={({ field }) => (
            <Textarea {...field} placeholder="Description of the project" />
          )}
        />
        <FormErrorMessage>{errors.description?.message}</FormErrorMessage>
      </FormControl>
      <DropdownWithInputText />
    </form>
  );
};

export default CreateProjectForm;
