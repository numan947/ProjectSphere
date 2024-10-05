import { Navigate } from "react-router-dom";
import useAuthStore from "../AuthStore";
import {
  Alert,
  AlertDescription,
  AlertIcon,
  AlertTitle,
  Button,
  FormControl,
  FormErrorMessage,
  FormLabel,
  HStack,
  Input,
  PinInput,
  PinInputField,
  SimpleGrid,
  Text,
} from "@chakra-ui/react";
import { useState } from "react";
import { useResetPassword } from "../hooks/useResetPassword";
import { z } from "zod";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

const PasswordResetSchema = z
  .object({
    pin: z.string().length(6, "Pin must be 6 characters"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z
      .string()
      .min(6, "Password must be at least 6 characters"),
  })
  .superRefine(({ confirmPassword, password }, ctx) => {
    if (confirmPassword !== password) {
      ctx.addIssue({
        code: "custom",
        message: "The passwords did not match",
        path: ["confirmPassword"],
      });
    }
  });

const defaultValues = {
  pin: "",
  password: "",
  confirmPassword: "",
};

type FormData = z.infer<typeof PasswordResetSchema>;

const ResetPassword = () => {
  const { token } = useAuthStore();
  if (token) {
    return <Navigate to="/home" />;
  }
  const [isSuccess, setSuccess] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const {
    handleSubmit,
    control,
    reset: resetForm,
    setValue,
    formState: { errors },
  } = useForm<FormData>({
    resolver: zodResolver(PasswordResetSchema),
    defaultValues: defaultValues,
  });

  const { isPending, mutate: submitResetRequest } = useResetPassword(
    () => {
      setSuccess(true);
      resetForm();
      setErrorMessage("");
    },
    (errorMsg: string) => {
      setSuccess(false);
      resetForm();
      setErrorMessage(errorMsg);
      setTimeout(() => {
        setErrorMessage("");
      }, 5000);
    }
  );

  const onSubmit = (data: FormData) => {
    submitResetRequest({
      password: data.password,
      token: data.pin,
    });
  };

  return (
    <SimpleGrid
      columns={1}
      spacing={4}
      padding={4}
      justifyItems="center"
      justifyContent="center"
    >
      <Text fontSize="4xl" fontWeight="bold">
        Reset Password
      </Text>
      <Text fontSize="xl">
        A code for resetting your password has been sent to your email. Please
        check your inbox and enter the code below. Then enter your new password.
      </Text>
      {
        // if the activation is successful, show success message
        isSuccess && (
          <Alert status="success" justifyContent="center">
            <AlertIcon />
            <AlertTitle>Reset Successful!</AlertTitle>
            <AlertDescription>Your password has been reset.</AlertDescription>
          </Alert>
        )
      }

      {errorMessage && (
        <Alert status="error" justifyContent="center">
          <AlertIcon />
          <AlertTitle>Error!</AlertTitle>
          <AlertDescription>{errorMessage}</AlertDescription>
        </Alert>
      )}

      <form onSubmit={handleSubmit(onSubmit)}>
        <FormControl isInvalid={!!errors.pin}>
          <FormLabel>Reset Code</FormLabel>
          <HStack mt={6}>
            <PinInput
              otp
              type="number"
              onChange={(value) => setValue("pin", value)}
              isDisabled={isPending || isSuccess}
              autoFocus
            >
              <PinInputField />
              <PinInputField />
              <PinInputField />
              <PinInputField />
              <PinInputField />
              <PinInputField />
            </PinInput>
          </HStack>
          <FormErrorMessage>{errors.pin?.message}</FormErrorMessage>
        </FormControl>
        <FormControl mt={4} isInvalid={!!errors.password}>
          <FormLabel>Password</FormLabel>
          <Controller
            name="password"
            control={control}
            render={({ field }) => (
              <Input
                isDisabled={isPending || isSuccess}
                {...field}
                type="password"
                placeholder="Enter your password"
              />
            )}
          />
          <FormErrorMessage>
            {errors.password && errors.password.message}
          </FormErrorMessage>
        </FormControl>

        <FormControl mt={4} isInvalid={!!errors.confirmPassword}>
          <FormLabel>Confirm Password</FormLabel>
          <Controller
            name="confirmPassword"
            control={control}
            render={({ field }) => (
              <Input
                isDisabled={isPending || isSuccess}
                {...field}
                type="password"
                placeholder="Confirm Password"
              />
            )}
          />
          <FormErrorMessage>
            {errors.confirmPassword && errors.confirmPassword.message}
          </FormErrorMessage>
        </FormControl>
        <HStack mt={4} pb={4} justifyContent="center">
          <Button
            type="submit"
            colorScheme="blue"
            isLoading={isPending}
            isDisabled={isSuccess}
          >
            Reset Password
          </Button>
        </HStack>
      </form>
    </SimpleGrid>
  );
};

export default ResetPassword;
